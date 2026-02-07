package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.database.dao.WeaponDao
import com.gaugustini.mhfudatabase.data.database.entity.weapon.WeaponParentEntity
import com.gaugustini.mhfudatabase.data.mapper.WeaponMapper
import com.gaugustini.mhfudatabase.domain.enums.WeaponType
import com.gaugustini.mhfudatabase.domain.filter.WeaponFilter
import com.gaugustini.mhfudatabase.domain.model.Weapon
import com.gaugustini.mhfudatabase.domain.model.WeaponNode
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Data repository for Weapon.
 */
@Singleton
class WeaponRepository @Inject constructor(
    private val weaponDao: WeaponDao,
) {

    /**
     * Returns the weapon with the given ID.
     */
    suspend fun getWeapon(
        weaponId: Int,
        language: String,
    ): Weapon {
        val weapon = weaponDao.getWeapon(weaponId, language)
        val weaponType = WeaponType.fromString(weapon.weapon.weaponType)
        val relatedWeapons = WeaponType.getRelatedWeapons(weaponType).map { it.name }
        val weaponList = weaponDao.getWeaponListByWeaponTypes(relatedWeapons, language)
            .map { WeaponMapper.toModel(it) }
        val weaponRelationList = weaponDao.getWeaponRelationsByWeaponIds(weaponList.map { it.id })
        val weaponGraph = WeaponGraph(weaponList, weaponRelationList)

        return WeaponMapper.toModel(
            weapon = weapon,
            ammoBow = weaponDao.getAmmoBowByWeaponId(weaponId),
            ammoBowgun = weaponDao.getAmmoBowgunByWeaponId(weaponId),
            recipeCreate = weaponDao.getWeaponRecipeByWeaponId(weaponId, "CREATE", language),
            recipeUpgrade = weaponDao.getWeaponRecipeByWeaponId(weaponId, "UPGRADE", language),
            paths = weaponGraph.findPathsToRoot(weaponId),
            finals = weaponGraph.findLeaves(weaponId),
        )
    }

    /**
     * Returns the list of all weapons or filtering by [WeaponFilter].
     * Note: ammo, recipe, paths and finals are not populated.
     */
    suspend fun getWeaponList(
        language: String,
        filter: WeaponFilter = WeaponFilter(),
    ): List<Weapon> {
        return weaponDao.getWeaponList(language).map { WeaponMapper.toModel(it) }
    }

    /**
     * Returns a list of weapons that are related to the given weapon type.
     */
    suspend fun getWeaponTree(
        weaponType: WeaponType,
        language: String,
    ): List<WeaponNode> {
        val relatedWeapons = WeaponType.getRelatedWeapons(weaponType).map { it.name }
        val weaponList = weaponDao.getWeaponListByWeaponTypes(relatedWeapons, language)
            .map { WeaponMapper.toModel(it) }
        val weaponRelationList = weaponDao.getWeaponRelationsByWeaponIds(weaponList.map { it.id })

        return WeaponGraph(weaponList, weaponRelationList).buildGraphByType(weaponType)
    }

}

class WeaponGraph(
    val weapons: List<Weapon>,
    val relations: List<WeaponParentEntity>,
) {
    private val nodes = weapons.associate { it.id to WeaponNode(it) }.toMutableMap()

    init {
        // Build relationship to nodes
        for (relation in relations) {
            val child = nodes[relation.weaponId]
            val parent = nodes[relation.parentWeaponId]

            if (child != null && parent != null) {
                child.parents.add(parent)
                parent.children.add(child)
            }
        }
    }

    fun buildGraphByType(type: WeaponType): List<WeaponNode> {
        val clonedNodes = weapons.associate { it.id to WeaponNode(it) }.toMutableMap()

        // Build relations between nodes of the same type
        for (relation in relations) {
            val child = clonedNodes[relation.weaponId]
            val parent = clonedNodes[relation.parentWeaponId]

            if (child != null && parent != null) {
                if (parent.weapon.type != type && child.weapon.type != type) {
                    continue
                }
                child.parents.add(parent)
                parent.children.add(child)
            }
        }

        // Remove loose nodes of another type
        val iterator = clonedNodes.iterator()
        for ((_, node) in iterator) {
            if (node.weapon.type != type && node.parents.isEmpty() && node.children.isEmpty()) {
                iterator.remove()
            }
        }

        return clonedNodes.values
            .filter { it.parents.isEmpty() }
            .sortedBy { it.weapon.rarity }
    }

    fun findPathsToRoot(weaponId: Int): List<List<Weapon>> {
        val startNode = nodes[weaponId] ?: return emptyList()

        fun dfs(node: WeaponNode, path: List<Weapon>): List<List<Weapon>> {
            val newPath = listOf(node.weapon) + path
            if (node.parents.isEmpty()) {
                return listOf(newPath)
            }

            return node.parents.flatMap { parent ->
                dfs(parent, newPath)
            }
        }

        return dfs(startNode, emptyList())
    }

    fun findLeaves(weaponId: Int): List<Weapon> {
        val startNode = nodes[weaponId] ?: return emptyList()
        val visitedLeaves = mutableSetOf<Int>()
        val leaves = mutableListOf<Weapon>()

        fun dfs(node: WeaponNode) {
            if (node.children.isEmpty()) {
                if (visitedLeaves.add(node.weapon.id)) {
                    leaves.add(node.weapon)
                }
                return
            }
            node.children.forEach { dfs(it) }
        }

        dfs(startNode)
        return leaves
    }

}
