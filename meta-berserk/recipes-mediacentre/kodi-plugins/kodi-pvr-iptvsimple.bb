SUMMARY = "pvr.iptvsimple addons for KODI Media Center"

ADDONS_NAME = "pvr.iptvsimple"
LICENSE = "GPL-2.0"
MD5_SUM = "md5=801f80980d171dd6425610833a22dbe6"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;${MD5_SUM}"

require ../kodi/kodi-dir.inc
require ../kodi/kodi-version.inc

DB_DIR = "${KODI_USERDATA}/Database"
SRC_URI = "git://github.com/xbmc/xbmc.git;branch=Krypton \
           file://db/Addons27.db \
           file://db/TV29.db \
          "
          
FULL_OPTIMIZATION_armv7a = "-fexpensive-optimizations -fomit-frame-pointer -O4 -ffast-math"
BUILD_OPTIMIZATION = "${FULL_OPTIMIZATION}"                  

inherit cmake gettext

S = "${WORKDIR}/git"


DEPENDS = "gettext bcm2835-bootfiles kodi unzip-native zip-native"
RDEPENDS_${PN} = "userland"

PACKAGES = "${PN}-dbg ${PN}"
PROVIDES = "${PACKAGES}"

FILES_${PN} = "/lib /share ${KODI_ADDON_DIR} ${DB_DIR}"
FILES_${PN}-dbg = "/lib/kodi/addons/${ADDONS_NAME}/.debug"

INSANE_SKIP_${PN} = "dev-so"

PARALLEL_MAKE = ""

# явно указываю firmware для Raspberry PI см. recipes-bsp/common/firmware.inc
# bitbake -s | grep "bcm2835-bootfiles" | cut -d":" -f2
VERSION_BCM2835 = "20180313-r3"
FIRM_DIR = "firmware-af994023ab491420598bfd5648b9dcab956f7e11"
GIT_DIR_BCM2835 = "bcm2835-bootfiles/${VERSION_BCM2835}/${FIRM_DIR}"
WORK_DIR_BCM2835 = "${BASE_WORKDIR}/${MACHINE}-${DISTRO}-${TARGET_OS}"

EXTRA_OECONF = " \
    --host=${HOST_SYS} \
    --build=${BUILD_SYS} \
    --prefix=${STAGING_DIR_TARGET}/usr/lib/kodi \
    --with-toolchain=${STAGING_DIR_TARGET} \
    --with-firmware=${WORK_DIR_BCM2835}/${GIT_DIR_BCM2835} \
    "

# конфигурация для платы RPI
EXTRA_OECONF_append_raspberrypi = " --with-platform=raspberry-pi"
# конфигурация для плат RPI2 и RPI3
EXTRA_OECONF_append_raspberrypi2 = " --with-platform=raspberry-pi2"


KODI_GEN_TOOLCHAIN = "${WORKDIR}/git/tools/depends/target/Toolchain.cmake"

# генерация Toolchain.cmake, сами зависимости не собираю,
# для сборки kodi они беруться из yocto project см. kodi_git.bb => DEPENDS
do_configure_prepend() {
    cd ${WORKDIR}/git/tools/depends
    sh bootstrap
    sh configure ${EXTRA_OECONF}

    # исправляю путь в cmake файле ${STAGING_DIR_TARGET}/usr/lib/kodi/KodiConfig.cmake
    # иначе при сборке addons не будет найден AddonHelpers
    sed -i "s| /usr/lib/kodi| ${STAGING_DIR_TARGET}/usr/lib/kodi|g" ${WORKDIR}/recipe-sysroot/usr/lib/kodi/KodiConfig.cmake
    sed -i "s| /usr/include/kodi| ${STAGING_DIR_TARGET}/usr/include/kodi|g" ${WORKDIR}/recipe-sysroot/usr/lib/kodi/KodiConfig.cmake
}


# явно указываю корневой системный каталог, иначе cmake тесты компилятора не проходят
CXXFLAGS += "--sysroot=${STAGING_DIR_TARGET}"
CFLAGS += "--sysroot=${STAGING_DIR_TARGET}"
# можно тоже самое сделать вот так
# echo "SET( CMAKE_SYSROOT ${STAGING_DIR_TARGET})" >> ${KODI_GEN_TOOLCHAIN}


do_configure() {
    echo "SET( OVERRIDE_PATHS 1)" >> ${KODI_GEN_TOOLCHAIN}
    
    cd ${B}
    cmake -DADDONS_TO_BUILD=${ADDONS_NAME} \
    -DCMAKE_BUILD_TYPE=Release \
    -DCMAKE_TOOLCHAIN_FILE=${KODI_GEN_TOOLCHAIN} \
    -DBUILD_DIR=${B} \
    -DCMAKE_INSTALL_PREFIX=${D} \
    -DPACKAGE_ZIP=1 \
    ${S}/project/cmake/addons
}



do_compile_append() {
    install -d ${D}/${KODI_ADDON_DIR}
    cp -vfR ${D}/pvr.iptvsimple ${D}/${KODI_ADDON_DIR}
    rm -fr ${D}/pvr.iptvsimple

    # автоматический запуск бинарного плагина pvr.iptvsimple при старте
    # содержимое базы можно посмотреть в "sqlitebrowser"
    install -d ${D}/${DB_DIR}
    install -m 0644 ${WORKDIR}/db/Addons27.db ${D}/${DB_DIR}
    install -m 0644 ${WORKDIR}/db/TV29.db ${D}/${DB_DIR}
}

# отключаю метод, так как после сборки файлы уже располагаются в CMAKE_INSTALL_PREFIX=${D}
do_install[noexec] = "1"



