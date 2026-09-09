# MHFU Database

<p>
  <a href="https://github.com/gaugustini/MHFUDatabase/releases"><img alt="Release" src="https://img.shields.io/github/v/release/gaugustini/MHFUDatabase"/></a>
  <a href="https://opensource.org/licenses/mit"><img alt="License" src="https://img.shields.io/github/license/gaugustini/MHFUDatabase"/></a>
  <a href="https://developer.android.com/tools/releases/platforms"><img alt="API" src="https://img.shields.io/badge/API-23%2B-brightgreen"/></a>
  <a href="https://github.com/gaugustini"><img alt="Profile" src="https://img.shields.io/badge/-gaugustini-blue?logo=github"/></a>
</p>

**MHFU Database** is an offline reference tool for **Monster Hunter Freedom Unite (MHFU)**, available
for Android, Windows, macOS, Linux, iOS, and the web.
Access a complete database of armors, weapons, items, skills, quests, and monsters — all with
detailed stats, drop rates, and descriptions.

This project is inspired by the apps developed by [Gathering Hall Studios](https://github.com/gatheringhallstudios), with most of the data sourced from [MHFU-DB](https://github.com/Kolyn090/mhfu-db) by Kolyn090.

## Features

Access game data with fast navigation, offline support, and clear cross-references between related
entities.

- **Comprehensive Game data**  – Detailed info on weapons, armors, items, skills, quests, gathering
  points, and monster drops.
- **Search & filters** – Quickly find items or equipment by name or category.
- **Smart cross-references** – Navigate the relationships between materials, monsters, weapons, and
  quests.
- **Offline access** – All content is stored locally for reliable use without an internet connection.

## Download

[<img src="https://play.google.com/intl/en_us/badges/images/generic/en-play-badge.png" alt="Get it on Google Play" height="80">](https://play.google.com/store/apps/details?id=com.gaugustini.mhfudatabase)

Or download the latest build for your platform (Android, Windows, macOS, Linux, or iOS) from the
[Releases](../../releases) page.

## Data

The app uses a local SQLite database. A pre-populated database is bundled with the app and copied to
the device on first launch (on the web, it's loaded into the browser's persistent storage instead).

Location of the database file:

[assets/database/data.db](assets/database/data.db)

## Development Setup

### Requirements

- Flutter SDK 3.13 or newer
- For Android: Android SDK and a JDK compatible with the Android Gradle Plugin (17 or newer)
- For Windows, macOS, or Linux: that platform's [desktop build requirements](https://docs.flutter.dev/platform-integration/desktop)
- For iOS: Xcode

### Building

1. Clone this repository:

```
git clone https://github.com/gaugustini/MHFUDatabase.git
```

2. Get the dependencies:

```
flutter pub get
```

   This also generates `lib/l10n/app_localizations*.dart` from the `.arb` files
   in `lib/l10n/`; those generated files aren't checked into version control.

3. Generate the database code:

```
dart run build_runner build
```

   This generates `lib/core/database/app_database.g.dart` from
   `lib/core/database/tables.drift`; that generated file isn't checked into
   version control.

4. Run the app on an Android emulator, a physical device, a desktop platform, or in a browser:

```
flutter run
```

To build a release for a specific platform from the command line:

```
flutter build apk      # Android
flutter build windows  # Windows
flutter build macos    # macOS
flutter build linux    # Linux
flutter build ios      # iOS
flutter build web      # Web
```

## Contributing

Whether you want to improve features, fix bugs, enhance documentation, or share new ideas — all contributions are welcome.

You can start by:
- Opening an issue to report a bug or suggest an improvement
- Submitting a pull request with your changes

### How to contribute

1. Fork this repository
2. Clone it to your local machine
3. Create a new branch for your changes
4. Commit and push your work to your fork
5. Open a pull request with a description of your contribution

## Credits and Resources

- [Gathering Hall Studios](https://github.com/gatheringhallstudios)
- [MHFU-DB](https://github.com/Kolyn090/mhfu-db) by Kolyn090
- [MHFU Blacksmith](https://mhfu.vallode.com/) by vallode
- [MHFU Wiki](https://monsterhunter.fandom.com/wiki/Monster_Hunter_Freedom_Unite)
- [MHP2G Wiki (Japanese)](https://w.atwiki.jp/mhp2g/)
- [GameFAQs](https://gamefaqs.gamespot.com/psp/943356-monster-hunter-freedom-unite)
    - [Guide and Walkthrough](https://gamefaqs.gamespot.com/psp/943356-monster-hunter-freedom-unite/faqs/78652)
      by ryin77
    - [Armor Set List](https://gamefaqs.gamespot.com/psp/943356-monster-hunter-freedom-unite/faqs/74198)
      by ZeoKnight
    - [Armor Skill/Decoration Jewel List](https://gamefaqs.gamespot.com/psp/943356-monster-hunter-freedom-unite/faqs/74199)
      by ZeoKnight
    - [Weapon Guide](https://gamefaqs.gamespot.com/psp/943356-monster-hunter-freedom-unite/faqs/57283)
      by Boldrin
- [FUComplete](https://fucomplete.github.io/)
- [mhp2g-patterns](https://github.com/IncognitoMan/mhp2g-patterns) by IncognitoMan
- [Neoseeker](https://monsterhunter.neoseeker.com/wiki/Monster_Hunter_Freedom_Unite_(PSP))
- [MHFU Texture Port](https://github.com/Monkbreh/MHFU-Texture-Port) - Item icons
- [Monster Hunter DB](https://github.com/CrimsonNynja/monster-hunter-DB) - Monster icons
- [zeedif](https://github.com/zeedif)

## License

This project is licensed under the [MIT License](LICENSE).
