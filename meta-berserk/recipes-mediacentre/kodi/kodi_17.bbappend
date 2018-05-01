FILESEXTRAPATHS_prepend := "${THISDIR}/kodi:"

SRCREV = "a9a7a20071bfd759e72e7053cee92e6f5cfb5e48"
PV = "17.6+gitr${SRCPV}"

# убираю не работающий патч (был для версии 17.3)
SRC_URI_remove = "file://0013-FTPParse.cpp-use-std-string.patch"

# отключаю патч, так как systemd не использую
# disable, because systemd is not used
SRC_URI_remove = "file://0004-handle-SIGTERM.patch"

