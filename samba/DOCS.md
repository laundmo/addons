# Home Assistant Add-on: Samba share

## Installation

Follow these steps to get the add-on installed on your system:

1. Navigate in your Home Assistant frontend to **Settings** -> **Add-ons** -> **Add-on store**.
2. Find the "Samba share" add-on and click it.
3. Click on the "INSTALL" button.

## How to use

1. In the configuration section set a username and password in the `users` block.
   You can specify any username and password; these are not related in any way to the login credentials you use to log in to Home Assistant or to log in to the computer with which you will use Samba share.
2. Save the configuration.
3. Start the add-on.
4. Check the add-on log output to see the result.

## Connection

If you are on Windows you use `\\<IP_ADDRESS>\<share_name>`, if you are on MacOS you use `smb://<IP_ADDRESS>/<share_name>` to connect to the share.

This addon exposes the following directories over smb/cifs by default:

| Directory       | Description                                                              |
| --------------- | ------------------------------------------------------------------------ |
| `addons`        | This is for your local add-ons.                                          |
| `addon_configs` | This is for the configuration files of your add-ons.                     |
| `backup`        | This is for your backups.                                                |
| `config`        | This is for your Home Assistant configuration.                           |
| `media`         | This is for local media files.                                           |
| `share`         | This is for your data that is shared between add-ons and Home Assistant. |
| `ssl`           | This is for your SSL certificates.                                       |

## Configuration

Add-on configuration:

```yaml
workgroup: WORKGROUP
compatibility_mode: false
veto_files:
  - ._*
  - .DS_Store
  - Thumbs.db
  - icon?
  - .Trashes
allow_hosts:
  - 10.0.0.0/8
  - 172.16.0.0/12
  - 192.168.0.0/16
  - 169.254.0.0/16
  - fe80::/10
  - fc00::/7
users:
  - username: homeassistant
    password: null
shares:
  - share_name: config
    path: /homeassistant
    deny: []
  - share_name: addons
    path: /addons
    deny: []
  - share_name: addon_configs
    path: /addon_configs
    deny: []
  - share_name: ssl
    path: /ssl
    deny: []
  - share_name: share
    path: /share
    deny: []
  - share_name: backup
    path: /backup
    deny: []
  - share_name: media
    path: /media
    deny: []
```

### Option: `workgroup` (required)

Change WORKGROUP to reflect your network needs.

### Option: `allow_hosts` (required)

List of hosts/networks allowed to access the shared folders.

### Option: `veto_files` (optional)

List of files that are neither visible nor accessible. Useful to stop clients
from littering the share with temporary hidden files
(e.g., macOS `.DS_Store` or Windows `Thumbs.db` files)

### Option: `compatibility_mode`

Setting this option to `true` will enable old legacy Samba protocols
on the Samba add-on. This might solve issues with some clients that cannot
handle the newer protocols, however, it lowers security. Only use this
when you absolutely need it and understand the possible consequences.

Defaults to `false`.

### Option `users` (at least 1 required)

A list of usernames and passwords for the users which have access to samba shares.

#### `username` (required)

A username you would like to use to authenticate with the Samba server.

#### `password` (required)

The password that goes with the username.

### Option: `shares` (at least 1 required)

A list of shared folders which samba will make accessible.

#### `share_name` (required)

The name of the samba shared folder, for use when connecting.

#### `path` (required)

The full path to the folder which this share will make accessible.

#### `deny` (optional)

A list of users which are not allowed to access this shared folder.

## Support

Got questions?

You have several options to get them answered:

- The [Home Assistant Discord Chat Server][discord].
- The Home Assistant [Community Forum][forum].
- Join the [Reddit subreddit][reddit] in [/r/homeassistant][reddit]

In case you've found a bug, please [open an issue on our GitHub][issue].

[discord]: https://discord.gg/c5DvZ4e
[forum]: https://community.home-assistant.io
[issue]: https://github.com/home-assistant/addons/issues
[reddit]: https://reddit.com/r/homeassistant
[repository]: https://github.com/hassio-addons/repository
