# Project: "Berserk" - build Kodi for the Raspberry Pi platform, autor: Alexander Demachev, site: https://berserk.tv
# license -  The MIT License (MIT)

DESCRIPTION = "Berserk - the image for the Raspberry PI"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

IMAGE_FEATURES += "ssh-server-dropbear splash"

# немного увеличиваю размер rootfs в кило байтах (100000kB=~100Mb)
IMAGE_ROOTFS_EXTRA_SPACE_append += "+ 100000"


# BS - BerSerk, version 0.2.5 - "Torvin"
# Base this image on core-image-minimal
include recipes-core/images/core-image-minimal.bb

# Set default password for 'root' user
inherit extrausers
ROOTUSERNAME = "root"
ROOTPASSWORD = "berserk"
EXTRA_USERS_PARAMS = "usermod -P ${ROOTPASSWORD} ${ROOTUSERNAME};"


# стартовая заставка, которая выводиться во время загрузки
SPLASH = "psplash-berserk"

BS_DEBUG_TOOLS = "ldd strace ltrace"

BS_GLIBC = "glibc-thread-db \
            glibc-gconv-utf-16 \
            glibc-gconv-utf-32 \
            glibc-gconv-koi8-r \
            glibc-gconv-cp1251 \
            glibc-gconv-ibm866 \
            glibc-binary-localedata-en-us \
            glibc-binary-localedata-ru-ru \
            glibc-binary-localedata-ru-ru.koi8-r \
            glibc-charmap-cp1251 \
            glibc-charmap-koi8-r \
            glibc-charmap-utf-8 \
            "

BS_BASE = "kernel-modules \
           lsb \
           pciutils \
           parted \
           tzdata \
           dosfstools \
           ntp \
           ntpdate \
           e2fsprogs-resize2fs \
           ntfs-3g \
           ntfsprogs \
           "

BS_WLAN = "kernel-module-rt2800usb \
           kernel-module-rt2800lib \
           kernel-module-rt2x00lib \
           kernel-module-rt2x00usb \
           kernel-module-cfg80211 \
           kernel-module-nls-utf8 \
           kernel-module-ath9k-common \
           kernel-module-ath9k-hw \
           kernel-module-ath9k-htc \
           kernel-module-ctr \
           kernel-module-ccm \
           kernel-module-arc4 \
           "

BS_WIFI_SUPPORT = " \
        iw \
        dhcp-client \
        wireless-tools \
        wpa-supplicant \
        linux-firmware \
        "

BS_SOFT = "mc \
           kodi \
           kodi-runner \
           kodi-settings \
           kodi-language-ru \
           kodi-pvr-iptvsimple \
           bs-net \
           tv-config \
           first-run \
           script-berserk-network \
           screensaver-kodi-universe \
           plugin-video-youtube \
           script-module-requests \
           "



# Include modules in rootfs
IMAGE_INSTALL += " \
    ${BS_BASE} \
    ${BS_WLAN} \
    ${BS_WIFI_SUPPORT} \
    ${BS_GLIBC} \
    ${BS_SOFT} \
    ${BS_DEBUG_TOOLS} \
    "
