# QuestLib
QuestLib offers you an easy to use questing library for your plugins. This library includes lots of features for you to create quests, objectives and give rewards

*Tested Minecraft versions: 1.8, 1.9, 1.10, 1.11, 1.12, 1.13, 1.14*
**You can use this library as a plugin or use it as a (shaded) dependency, see the [documentation](https://questlib.themelvin.nl/docs/)**

> Warning: This library has no stable release yet, if you encounter any bugs, please report them in the issues section.

## Features
* Built-in YAML or MySQL storage.
* Custom reward types.
* Multiple custom objectives.
* Easily configurable.
* Customisable to your own wishes.
* Extensive API.

## Example
Quest class:
```java
public class BreakQuest extends Quest {

    public BreakQuest() {

        this.setName("Break Quest");
        this.setDescription("Break different kind of blocks");

        this.setStartMessage("&bQuest started: " + this.getName());
        this.setCompleteMessage("&bQuest finished: " + this.getName());

        this.setReward(new MultiReward(new ItemReward(new ItemStack(Material.EMERALD, 2)), new ItemReward((new ItemStack(Material.IRON_INGOT, 10)))));

        this.addObjective(new CobblestoneObjective());

    }

}
```

Cobblestone objective:
```java
public class CobblestoneObjective extends QuestObjective {

    public CobblestoneObjective() {

        this.setName("Cobblestone objective");
        this.setDescription("Break 5 cobblestone blocks.");
        this.setReward(new ItemReward(new ItemStack(Material.DIAMOND, 1), "&6You received &e1x Diamond &6as a reward."));

        this.setStartMessage("&b" + this.getName() + ": " + this.getDescription());
        this.setCompleteMessage("&aYou finished this objective.");

    }

    @EventHandler
    public void blockBreak(BlockBreakEvent event) {

        // This check is neccessary, as QuestLib is not able to recognise events.
        if(!this.isFrom(event.getPlayer())) return;

        if(event.getBlock().getType() != Material.COBBLESTONE) {
            return;
        }

        this.incrementData("count", 1);

        if(this.getDataInt("count") >= 5) {
            this.complete();
        }

    }

}
```

As you can see, with QuestLib you're able to easily create new objectives and register them in your quest.
QuestLib also saves all the data you provide using `setData()` or `incrementData()` (for integers).

If you want more examples, have a look at the [Example repository](https://github.com/TheMelvinNL/QuestLib-Example).

## Documentation
Please read the [Wiki](https://questlib.themelvin.nl/docs/) as this library is somewhat hard to understand without it.

## Setup
1. Download and install [Gradle](https://gradle.org/).
1. Import the repo as **Gradle** project.
1. Run [BuildTools](https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar) 1.8.8 `java -jar BuildTools.jar --rev 1.8.8`.
4. Compile: `gradle build`

## Contributing
* New features and bug fixes should be developed and tested in a new branch.
* Feature branches should always be merged with development.
* Commits should be prefixed using [GitMoji](https://gitmoji.carloscuesta.me/).