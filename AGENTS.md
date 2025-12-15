# AGENTS.md - Rapid Leaf Decay

## Projekt-Übersicht

**Rapid Leaf Decay** ist ein NeoForge Minecraft Mod für Minecraft 1.21.1.
- **Mod ID**: `rapid_leaf_decay`
- **Package**: `de.geheimagentnr1.rapid_leaf_decay`
- **Java Version**: 21
- **NeoForge Version**: 21.1.x

Lässt Blätter schnell zerfallen, nachdem der zugehörige Baumstamm entfernt wurde.

## Abhängigkeiten

Keine Mod-Abhängigkeiten - eigenständiger Mod.

## Projektstruktur

```
src/main/java/de/geheimagentnr1/rapid_leaf_decay/
├── RapidLeafDecay.java          # Haupt-Mod-Klasse
├── config/
│   └── ServerConfig.java        # Server-Konfiguration
├── decayer/
│   ├── DecayQueue.java          # Queue für Decay-Tasks
│   ├── DecayTask.java           # Einzelner Decay-Task
│   └── DecayWorker.java         # Worker für Decay-Verarbeitung
├── handlers/
│   └── DecayWorkHandler.java    # Event-Handler für Decay
└── helpers/
    └── LeavesHelper.java        # Helper für Blätter-Erkennung
```

## Besonderheiten

- **Queue-basiertes System**: Blätter werden in einer Queue verarbeitet
- **Worker-Pattern**: DecayWorker verarbeitet Tasks asynchron
- **Konfigurierbar**: Decay-Geschwindigkeit über ServerConfig einstellbar

## Code-Stil

- **Annotations**: `@NotNull` aus `org.jetbrains.annotations`
- **Lombok**: Projekt nutzt Lombok
- **Formatierung**: Leerzeichen nach `(` und vor `)` bei Methodenaufrufen

## Build & Test

```bash
./gradlew build
./gradlew runClient
./gradlew runServer
```

## Deployment

- **CurseForge**: `./gradlew curseforge`
- **Modrinth**: `./gradlew modrinth`
