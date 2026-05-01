# CLAUDE.md - Rapid Leaf Decay

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

## Testing

### Java-Versionen

Verschiedene Java-Versionen sind unter `C:\Program Files\Eclipse Adoptium` installiert. Für einen Gradle-Build muss die passende Java-Version gewählt werden:

```powershell
# Java 21 für MC 1.20.5+ (NeoForge)
$env:JAVA_HOME = "C:\Program Files\Eclipse Adoptium\jdk-21.0.9.10-hotspot"
./gradlew build
```

### Unit Tests (JUnit 5)

Für reine Logik-Tests ohne Minecraft-Abhängigkeiten:

```bash
./gradlew test
```

Tests liegen unter `src/test/java/`. Ergebnisse: `build/reports/tests/test/index.html`

### NeoForge GameTest Framework

Für Integration Tests in einer echten Minecraft-Umgebung:

```bash
./gradlew runGameTestServer
```

GameTest-Klassen werden mit `@GameTestHolder` annotiert und liegen unter `src/main/java/.../elements/gametests/`.

### CI/CD (GitHub Actions)

Der Workflow `.github/workflows/build-and-test.yml` führt automatisch aus:
1. **Build**: Kompiliert den Mod
2. **Unit Tests**: Führt JUnit Tests aus
3. **GameTests**: Startet GameTestServer (optional)

### Was kann automatisiert getestet werden?

| Aspekt | Automatisiert? | Methode |
|--------|----------------|---------|
| Utility-Klassen | ✅ | JUnit |
| Config-Parsing | ✅ | JUnit |
| Commands | ✅ | GameTest |
| Block/Item-Verhalten | ✅ | GameTest |
| Multi-MC-Version | ⚠️ Pro Branch | CI Matrix |
