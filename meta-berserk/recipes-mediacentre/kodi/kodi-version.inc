# ветка Isengard
SRCREV = "02e7013889e08d608363f2909ebeccdb9ea3b7c9"
FILESEXTRAPATHS_prepend := "${THISDIR}/kodi:"

PV = "15.0+gitr${SRCPV}"
PR = "r0"
            
SRC_URI = "git://github.com/xbmc/xbmc.git;branch=Isengard \
           file://kodi-isengard-rpb-backports.patch \
           file://network-menu.patch \
          "

