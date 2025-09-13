package com.gaugustini.mhfudatabase.data.model

import com.gaugustini.mhfudatabase.data.enums.WeaponType

class WeaponGraph(
    val weapons: List<Weapon>,
    val relations: List<WeaponRelation>,
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
