# Spyglass Astronomy Sync
Spyglass Astronomy Sync is an extension to [Spyglass Astronomy](https://modrinth.com/mod/spyglass-astronomy), that adds
server support. With this mod installed all players will share the same sky automatically, without needing to
manually sync via commands.

## Configuration
By default, Spyglass Astronomy Sync disables most `/sga:admin` commands. This behavior can be changed in the
configuration file, located at `config/spyglass-astronomy-sync.json`.

Default configuration:
```json
{
  "allow_admin_commands": false
}
```
- Set `"allow_admin_commands"` to `true` to allow all `/sga:admin` commands for every player.
- Players with permission
level 2 or higher will bypass this setting.
- The configuration file is only read at server startup. Restart the server after making changes for them to take
effect.

## Dependencies
To use Spyglass Astronomy Sync:
- [Fabric API](https://modrinth.com/mod/fabric-api) must be installed.
- [Spyglass Astronomy](https://modrinth.com/mod/spyglass-astronomy) is required on the client, the server does not need it.

Both the server and the client must have Spyglass Astronomy Sync installed for synchronization to take effect.