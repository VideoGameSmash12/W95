# W95
W95 was a client I created for 1.16.5 which was intended for administrative use. It was privately distributed to admins
at the time, and was very rough around the edges. When I attempted to update the mod for 1.17.1, I quickly realized how
much of a clusterfuck its code actually was and began to work on a rewrite of the project.

The rewrite took a few months to complete, but eventually became its own project, called WNT. The rewrite immediately
replaced the original. I've since decided to publicize the original source code because I have no interest in
maintaining it.

## Where does the name come from?
The name "W95" comes from the fact that I had planned to implement a ClickGUI screen that would have looked a lot like
the original Windows 9x look. However, I scrapped the idea as soon as I realized I didn't know how I would do a ClickGUI
sort of menu in the first place.

## Supervisor
The Supervisor was the powerhouse tool of W95. I had intended for it to essentially allow for full control of the client
with several tabs depicting various features, but unfortunately it never got that far. What was implemented, however, is
listed below:
* Limited entity data list
* Tile entity data list
* Mitigations
* Chat
* Rendering
* F3 Menu

Here's a screenshot of it. You can see how different it is compared to WNT's Blackbox in terms of layout.
![https://media.videogamesm12.me/OzlfLzQbPshdXOM0.png](https://media.videogamesm12.me/OzlfLzQbPshdXOM0.png)

## Commands
W95's command system used a hybrid of the TotalFreedomMod's way of loading commands and a Bukkit-like way of handling
commands with booleans. All commands are registered under one single command: /w95. Here's a list of subcommands that
were implemented:
* /w95 dump <entities \[ids]>
* /w95 entities <dump \<id> | dumpall | info | list | query \<id>>
* /w95 modules \[module]
* /w95 supervisor \<show>

Most of this system was ultimately scrapped because the idea of using Reflections to load commands was stupid to begin
with.