# CLAUDE.md - Rapid Leaf Decay

## Projekt-Übersicht

**Rapid Leaf Decay** ist ein NeoForge Minecraft Mod.
- **Mod ID**: `rapid_leaf_decay`
- **Package**: `de.geheimagentnr1.rapid_leaf_decay`
- **Java Version**: 21
- **NeoForge Version**: je Branch, siehe Tabelle

Lässt Blätter schnell zerfallen, nachdem der zugehörige Baumstamm entfernt wurde.

| Branch | MC | Range | NeoForge (kompiliert gegen) | Hinweis |
|---|---|---|---|---|
| `develop_1.21.1` | 1.21.1 - 1.21.10 | `[1.21.1,1.21.10]` | `21.1.216` | Release `1.21.1-3.0.1` |
| `develop_1.21.11` | 1.21.11 | `[1.21.11,1.21.12)` | `21.11.45` | Release `1.21.11-3.0.1` (2026-10-02); keine Java-Änderungen, trivialer GameTest samt Run-Config und CI-Job entfernt, JUnit in `build.gradle` ergänzt |

`develop_1.21.3` ist ein alter, nur lokaler Forge-Stand (`forge_version`) und kein NeoForge-Port.

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
$env:JAVA_HOME = "C:\Program Files\Eclipse Adoptium\jdk-21.0.12.8-hotspot"
./gradlew build
```

### Ingame-Test

Natürlichen Baum fällen (alle Stammblöcke) - Blätter zerfallen in wenigen Sekunden; selbst platzierte (persistente) Blätter und Blätter an einem zweiten Stamm bleiben. Kein Weltzustand gespeichert, ein Neustart-Test ist nicht nötig.

### Unit Tests (JUnit 5)

Für reine Logik-Tests ohne Minecraft-Abhängigkeiten:

```bash
./gradlew test
```

Tests liegen unter `src/test/java/`. Ergebnisse: `build/reports/tests/test/index.html`

### NeoForge GameTest Framework

Ab `develop_1.21.11` gibt es keine GameTests mehr (trivialer Smoke-Test samt Run-Config und CI-Job entfernt; das annotationsbasierte Framework existiert seit 1.21.5 nicht mehr).

### CI/CD (GitHub Actions)

Der Workflow `.github/workflows/build-and-test.yml` führt automatisch aus:
1. **Build**: Kompiliert den Mod
2. **Unit Tests**: Führt JUnit Tests aus

### Was kann automatisiert getestet werden?

| Aspekt | Automatisiert? | Methode |
|--------|----------------|---------|
| Utility-Klassen | ✅ | JUnit |
| Config-Parsing | ✅ | JUnit |
| Commands | ✅ | GameTest |
| Block/Item-Verhalten | ✅ | GameTest |
| Multi-MC-Version | ⚠️ Pro Branch | CI Matrix |

## Referenzen

- [NeoForge Migration Primer](https://docs.neoforged.net/primer/docs/) — Dokumentiert API-Aenderungen zwischen Minecraft/NeoForge-Versionen; nuetzlich fuer die Pruefung von Breaking Changes beim Upgrade auf neue Versionen

---

## Wissensdatenbank

Versionsübergreifende Migrations- und Entwicklungs-Erkenntnisse (Breaking Changes, Fixes, Testumgebungs-Patterns) werden zentral in [`../Docs/`](../Docs/) gepflegt. Bei neuen relevanten Erkenntnissen dort ergänzen, nicht nur hier.
