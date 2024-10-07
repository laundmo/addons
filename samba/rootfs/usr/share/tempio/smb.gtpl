#test
[global]
   netbios name = {{ env "HOSTNAME" }}
   workgroup = {{ .workgroup }}
   server string = Samba Folder Home Assistant

   security = user
   ntlm auth = yes
   idmap config * : backend = tdb
   idmap config * : range = 1000000-2000000

   load printers = no
   disable spoolss = yes

   log level = 1

   bind interfaces only = yes
   interfaces = 127.0.0.1 {{ .interfaces | join " " }}
   hosts allow = 127.0.0.1 {{ .allow_hosts | join " " }}

   {{- if .compatibility_mode -}}
   client min protocol = NT1
   server min protocol = NT1
   {{- end -}}

   mangled names = no
   dos charset = CP850
   unix charset = UTF-8

{{ $global := . -}}
{{ range $share_env := .shares -}}
   {{/* build a allowed users list which includes those not denied */}}
   {{ $allowedUsers := list -}}
   {{ range $users_env := $global.users -}}
      {{ if not (has $users_env.username $share_env.deny) -}}
      {{ $allowedUsers = append $allowedUsers $users_env.username -}}
      {{ end -}}
   {{ end -}}

[{{$share_env.share_name}}]
   browseable = yes
   writeable = yes
   path = {{$share_env.path}}

   valid users = {{ $allowedUsers | join " " }}
   force user = root
   force group = root
   veto files = /{{ $global.veto_files | join "/" }}/
   delete veto files = {{ eq (len $global.veto_files) 0 | ternary "no" "yes" }}

{{ end -}}