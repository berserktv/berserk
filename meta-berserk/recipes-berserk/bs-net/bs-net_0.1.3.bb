# Simple network "shell" extension for wlan/ethernet 
# Project "Berserk", autor Alexander Demachev, site https://berserk.tv
# license -  The MIT License (MIT)

DESCRIPTION = "Simple network shell extension for wlan/ethernet"
SECTION = "nets"
PR = "r1"
LICENSE = "MIT"

LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"


SRC_URI = "git://github.com/berserktv/bs-net.git;branch=master"
SRCREV = "2d95807ffd285f66f60eae021baaed645d68fb43"

require recipes-mediacentre/kodi/kodi-dir.inc

NET_DIR = "/etc/network"
UDEV_DIR = "/etc/udev/rules.d"
ETH_CONFIGS = "${KODI_USERDATA}/eths"
WLAN_CONFIGS = "${KODI_USERDATA}/wlans"

S = "${WORKDIR}/git"

PACKAGES = "${PN}"
FILES_${PN} = "${NET_DIR} ${UDEV_DIR} ${KODI_HOME_DIR}"

RDEPENDS_${PN} += "ntp wpa-supplicant iw gawk"

do_install_append() {
    install -d ${D}${UDEV_DIR}
    install -d ${D}${sysconfdir}/network
    install -d ${D}${ETH_CONFIGS}
    install -d ${D}${WLAN_CONFIGS}

    install -m 0755 ${S}${NET_DIR}/dh-func.sh ${D}${sysconfdir}/network
    install -m 0755 ${S}${NET_DIR}/eth-manual ${D}${sysconfdir}/network 
    install -m 0755 ${S}${NET_DIR}/tools-wifi.sh ${D}${sysconfdir}/network
    install -m 0755 ${S}${NET_DIR}/wlan ${D}${sysconfdir}/network
    install -m 0755 ${S}${NET_DIR}/wlan-runner ${D}${sysconfdir}/network
    install -m 0644 ${S}${NET_DIR}/eth0 ${D}${ETH_CONFIGS}

    install -m 0644 ${S}${UDEV_DIR}/80-wifi-start.rules ${D}${UDEV_DIR} 
    install -m 0644 ${S}${UDEV_DIR}/85-automount.rules  ${D}${UDEV_DIR}
}
