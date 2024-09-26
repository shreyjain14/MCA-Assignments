# Hypixel Skyblock Stranded Leaderboard System - Database Schema Documentation

## Overview

This document outlines the database schema for the Hypixel Skyblock Leaderboard System. The system is designed to store and manage player data, game statistics, and leaderboard information for the Hypixel Skyblock game mode.

## Entities

### 1. Player

Represents a Hypixel player.

| Attribute    | Type      | Description                               |
|--------------|-----------|-------------------------------------------|
| uuid         | String    | Primary Key. Unique identifier for player |
| username     | String    | Player's username                         |
| lastUpdated  | Timestamp | Last time player data was updated         |

### 2. Profile

Represents a Skyblock profile for a player. A player can have multiple profiles.

| Attribute      | Type      | Description                               |
|----------------|-----------|-------------------------------------------|
| id             | Integer   | Primary Key. Auto-incrementing identifier |
| profileUuid    | String    | Unique identifier for the profile         |
| profileCuteName| String    | Custom name for the profile               |
| firstJoin      | Timestamp | When the profile was first created        |

### 3. SkillsStats

Stores skill levels for a profile.

| Attribute    | Type    | Description                               |
|--------------|---------|-------------------------------------------|
| id           | Integer | Primary Key. Auto-incrementing identifier |
| alchemy      | Double  | Alchemy skill level                       |
| carpentry    | Double  | Carpentry skill level                     |
| combat       | Double  | Combat skill level                        |
| enchanting   | Double  | Enchanting skill level                    |
| farming      | Double  | Farming skill level                       |
| fishing      | Double  | Fishing skill level                       |
| foraging     | Double  | Foraging skill level                      |
| mining       | Double  | Mining skill level                        |
| runecrafting | Double  | Runecrafting skill level                  |
| social       | Double  | Social skill level                        |
| taming       | Double  | Taming skill level                        |

### 4. SlayerStats

Stores slayer experience for a profile.

| Attribute | Type    | Description                               |
|-----------|---------|-------------------------------------------|
| id        | Integer | Primary Key. Auto-incrementing identifier |
| zombie    | Integer | Zombie slayer experience                  |
| spider    | Integer | Spider slayer experience                  |
| wolf      | Integer | Wolf slayer experience                    |
| enderman  | Integer | Enderman slayer experience                |
| blaze     | Integer | Blaze slayer experience                   |

### 5. BestiaryStats

Stores bestiary information for a profile.

| Attribute              | Type    | Description                               |
|------------------------|---------|-------------------------------------------|
| id                     | Integer | Primary Key. Auto-incrementing identifier |
| last_claimed_milestone | Integer | Last claimed bestiary milestone           |
| kills                  | JSON    | JSON object containing kill counts        |

### 6. CollectionStats

Stores collection data for a profile.

| Attribute          | Type    | Description                               |
|--------------------|---------|-------------------------------------------|
| id                 | Integer | Primary Key. Auto-incrementing identifier |
| farmingCollections | JSON    | JSON object for farming collections       |
| miningCollections  | JSON    | JSON object for mining collections        |
| combatCollections  | JSON    | JSON object for combat collections        |
| foragingCollections| JSON    | JSON object for foraging collections      |
| fishingCollections | JSON    | JSON object for fishing collections       |

### 7. GardenStats

Stores garden-related statistics for a profile.

| Attribute    | Type    | Description                               |
|--------------|---------|-------------------------------------------|
| id           | Integer | Primary Key. Auto-incrementing identifier |
| doubleDrops  | Integer | Double drop counter                       |
| wheat        | Integer | Wheat counter                             |
| pumpkin      | Integer | Pumpkin counter                           |
| cocoBean     | Integer | Cocoa bean counter                        |
| sugarCane    | Integer | Sugar cane counter                        |
| potato       | Integer | Potato counter                            |
| carrot       | Integer | Carrot counter                            |
| melon        | Integer | Melon counter                             |
| cactus       | Integer | Cactus counter                            |
| netherWart   | Integer | Nether wart counter                       |
| mushroom     | Integer | Mushroom counter                          |

### 8. ChocolateFactoryStats

Stores chocolate factory statistics for a profile.

| Attribute      | Type    | Description                               |
|----------------|---------|-------------------------------------------|
| id             | Integer | Primary Key. Auto-incrementing identifier |
| totalChocolate | Long    | Total chocolate produced                  |
| chocolateSpent | Long    | Total chocolate spent                     |
| uniqueRabbits  | Integer | Number of unique rabbits collected        |

### 9. MiscellaneousStats

Stores various miscellaneous statistics for a profile.

| Attribute               | Type    | Description                               |
|-------------------------|---------|-------------------------------------------|
| id                      | Integer | Primary Key. Auto-incrementing identifier |
| spookyCandy             | Integer | Spooky candy collected                    |
| highestCriticalDamage   | Integer | Highest critical damage dealt             |
| highestDamage           | Integer | Highest damage dealt                      |
| kills                   | Integer | Total kills                               |
| deaths                  | Integer | Total deaths                              |
| itemsFished             | Integer | Total items fished                        |
| summoningEyesCollected  | Integer | Total summoning eyes collected            |
| uniqueMinionUnlocked    | Integer | Number of unique minions unlocked         |

### 10. Leaderboard

Represents a leaderboard in the system.

| Attribute | Type      | Description                               |
|-----------|-----------|-------------------------------------------|
| id        | Integer   | Primary Key. Auto-incrementing identifier |
| name      | String    | Name of the leaderboard                   |
| category  | String    | Category of the leaderboard               |
| updatedAt | Timestamp | Last update time of the leaderboard       |

### 11. LeaderboardEntry

Represents an entry on a leaderboard.

| Attribute     | Type       | Description                               |
|---------------|------------|-------------------------------------------|
| id            | Integer    | Primary Key. Auto-incrementing identifier |
| score         | BigDecimal | Score for this leaderboard entry          |
| rank          | Integer    | Rank on the leaderboard                   |

## Relationships

1. Player (1) -> (0..*) Profile
2. Profile (1) -> (1) SkillsStats
3. Profile (1) -> (1) SlayerStats
4. Profile (1) -> (1) BestiaryStats
5. Profile (1) -> (1) CollectionStats
6. Profile (1) -> (1) GardenStats
7. Profile (1) -> (1) ChocolateFactoryStats
8. Profile (1) -> (1) MiscellaneousStats
9. Profile (1) -> (0..*) LeaderboardEntry
10. Leaderboard (1) -> (0..*) LeaderboardEntry

## Notes

- All `id` fields are auto-incrementing integers unless otherwise specified.
- The `Player.uuid` field serves as both the primary key and the unique identifier for players.
- JSON fields (e.g., in BestiaryStats and CollectionStats) should be implemented using the appropriate JSON data type in your chosen database system.
- Consider adding indexes on frequently queried fields and foreign keys to optimize performance.
- Implement appropriate cascading rules for entity relationships, especially for the one-to-one relationships with Profile.

This schema provides a comprehensive structure for storing and managing Hypixel Skyblock Stranded player data and leaderboard information. It allows for efficient querying of player statistics and leaderboard rankings while maintaining data integrity through proper relationships between entities.