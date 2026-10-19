# CLAUDE.md - Rapid Leaf Decay

Rapid Leaf Decay lässt Blätter innerhalb weniger Sekunden zerfallen, sobald ihr Baumstamm entfernt wurde (statt über Vanilla-Random-Ticks). Mod-ID `rapid_leaf_decay`, Package `de.geheimagentnr1.rapid_leaf_decay`. Client+Server ohne Dist-Beschränkung (`@Mod( MODID )`); die Logik läuft nur auf dem (auch integrierten) Server, es gibt keine Registrierungen und keine Netzwerkpakete.

> Diese Datei ist auf allen `develop_*`-Branches identisch; Quelle: `../Docs/mod-claude/RapidLeafDecay.md` (Änderungen dort, dann `Docs/tools/sync_mod_claude_md.sh`). Versionsstand: [`../Docs/VERSIONEN.md`](../Docs/VERSIONEN.md).

## Versionen

- Branches, Ranges und Tags: Zeile „RapidLeafDecay“ in [`../Docs/VERSIONEN.md`](../Docs/VERSIONEN.md).
- NeoForge: `develop_1.21.1` (ein Jar 1.21.1 - 1.21.10), `develop_1.21.11`, `develop_26.1` (26.1 - 26.2), `develop_26.3` (ab NeoForge 26.3.0.37-beta: Config-Typ `SYNCED` statt `SERVER` mit altem Dateinamen `<modid>-server.toml`, alte `world/serverconfig/`-Datei wird auf Dedicated Servern einmalig nach `world/syncedconfig/` kopiert - `config/ServerConfigRegistration`, `config/LegacyServerConfigMigration`, 2026-10-19). Forge: `develop_<mc>` bis 1.21.3 und `develop_1.21.1_forge`.
- Der Java-Code ist auf allen NeoForge-Branches gleich; 1.21.11 und 26.x unterscheiden sich nur in Tooling/NeoForge-Version und im entfernten GameTest.

## Abhängigkeiten

- Keine Mod-Abhängigkeiten, keine gebündelten Bibliotheken.
- Forge ab 1.20: MinecraftForgeAPI (JarInJar, siehe [`../Docs/forge/README.md`](../Docs/forge/README.md)).

## Architektur

- `RapidLeafDecay` - Hauptklasse: registriert `ServerConfig.SPEC` (Typ SERVER) und `DecayWorkHandler` auf dem NeoForge-Bus.
- `handlers/DecayWorkHandler` - `BlockEvent.NeighborNotifyEvent`: wird ein Block leer, kommen benachbarte, nicht persistente Blätter als `DecayTask` in die `DecayQueue`; `ServerTickEvent.Post` treibt den `DecayWorker`; `ServerStartingEvent` initialisiert die Queue.
- `decayer/DecayQueue` - `TreeSet` nach Dimension und Position (keine Duplikate), wird pro Durchlauf geleert.
- `decayer/DecayWorker` - alle `decay_delay` Ticks: berechnet die Blatt-Distanzen neu (Flood-Fill über verbundene Blätter) und löst einen `randomTick` aus, der das Blatt bei Distanz 7 zerfallen lässt. Läuft synchron im Server-Tick, nicht asynchron.
- `helpers/LeavesHelper` - erkennt Blätter (alles mit `LeavesBlock.DISTANCE`, auch Stämme mit dieser Eigenschaft) und prüft `PERSISTENT`.
- Forge-Branches: `DecayWorker` ist ein `WorldWorkerManager.IWorker` (ab 1.20 mit `AbstractMod` aus MinecraftForgeAPI, bis 1.19.4 statische `handlers/ModEventHandler`/`ForgeEventHandler`). NeoForge nutzt `ServerTickEvent.Post`, weil `WorldWorkerManager.IWorker` später entfällt.

## Funktionen, Befehle, Konfiguration

- Keine Befehle, keine Items/Blöcke.
- Server-Config (im `serverconfig/`-Ordner der Welt): `decay_delay` = Ticks zwischen zwei Zerfallsdurchläufen (Standard 5, `0` = so lange in einem Tick, bis die Queue leer ist).
- Kein gespeicherter Weltzustand; die Queue lebt nur im Speicher.

## Fallstricke

- Selbst platzierte Blätter (`persistent=true`) dürfen nie zerfallen - jede Änderung an `LeavesHelper` daraufhin prüfen.
- `NeighborNotifyEvent` feuert sehr oft; im Handler nur billige Prüfungen, die Arbeit gehört in den Worker.
- 1.21.11+ keine GameTests mehr (annotationsbasiertes Framework seit 1.21.5 weg, der triviale Test wurde entfernt).
- Forge 1.20.1: der Dedicated-Server-Absturz ab Forge 47.4.16 (`MultiPlayerGameMode` … `invalid dist`, GH#5) lag an MinecraftForgeAPI und ist mit MFAPI 1.0.2 behoben ([`../Docs/forge/README.md`](../Docs/forge/README.md)).

## Testen

- Allgemeines Vorgehen: [`../Docs/testing/README.md`](../Docs/testing/README.md); Jar in Server-Testpack und Client-Instanz.
- Ingame: natürlichen Baum komplett fällen (alle Stammblöcke) - Blätter zerfallen in wenigen Sekunden; selbst platzierte Blätter und Blätter, die noch an einem zweiten Stamm hängen, bleiben. Kein Neustart-Test nötig (kein Weltzustand).
- Server-Smoke über alle Packs: [`../Docs/testing/tools/neoforge_smoke_all.sh`](../Docs/testing/tools/neoforge_smoke_all.sh). CI: Build + JUnit.

## Links

- Wiki: https://github.com/GeheimagentNr1/RapidLeafDecay/wiki
- CurseForge: https://www.curseforge.com/minecraft/mc-mods/rapid-leaf-decay (Projekt-ID 396847)
- Modrinth: https://modrinth.com/mod/jSQXzmcf
- [`../Docs/migrations/BREAKING_CHANGES.md`](../Docs/migrations/BREAKING_CHANGES.md), [`../Docs/releasing/release-process.md`](../Docs/releasing/release-process.md)
