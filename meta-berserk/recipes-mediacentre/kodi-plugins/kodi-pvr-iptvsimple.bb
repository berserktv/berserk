SUMMARY = "pvr.iptvsimple addons for KODI Media Center"

ADDONS_NAME = "pvr.iptvsimple"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

require ../kodi/kodi-version.inc


FULL_OPTIMIZATION_armv7a = "-fexpensive-optimizations -fomit-frame-pointer -O4 -ffast-math"
BUILD_OPTIMIZATION = "${FULL_OPTIMIZATION}"                  

inherit cmake gettext

S = "${WORKDIR}/git"


DEPENDS = "gettext bcm2835-bootfiles kodi"
RDEPENDS_${PN} = "userland"

PACKAGES = "${PN}-dbg ${PN}"
PROVIDES = "${PACKAGES}"

FILES_${PN} = "/lib /share /home/root/.kodi/addons"
FILES_${PN}-dbg = "/lib/kodi/addons/pvr.iptvsimple/.debug"

INSANE_SKIP_${PN} = "dev-so"

PARALLEL_MAKE = ""

# явно указываю firmware для Raspberry PI см. recipes-bsp/common/firmware.inc
# bitbake -s | grep "bcm2835-bootfiles" | cut -d":" -f2
VERSION_BCM2835 = "20160622-r3"
GIT_DIR_BCM2835 = "bcm2835-bootfiles/${VERSION_BCM2835}/git"
WORK_DIR_BCM2835 = "${BASE_WORKDIR}/${MACHINE}-${DISTRO}-${TARGET_OS}"
KODI_HOME_DIR = "/home/root/.kodi"

EXTRA_OECONF = " \
    --with-platform=raspberry-pi \
    --host=${HOST_SYS} \
    --build=${BUILD_SYS} \
    --prefix=${STAGING_DIR_TARGET}/usr/lib/kodi \
    --with-toolchain=${STAGING_DIR_TARGET} \
    --with-firmware=${WORK_DIR_BCM2835}/${GIT_DIR_BCM2835} \
    "

KODI_GEN_TOOLCHAIN = "${WORKDIR}/git/tools/depends/target/Toolchain.cmake"

# генерация Toolchain.cmake, сами зависимости не собираю,
# для сборки kodi они беруться из yocto project см. kodi_git.bb => DEPENDS
do_configure_prepend() {
    cd ${WORKDIR}/git/tools/depends
    sh bootstrap
    sh configure ${EXTRA_OECONF}
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
    install -d ${D}/${KODI_HOME_DIR}/addons
    cp -vfR ${D}/pvr.iptvsimple ${D}/${KODI_HOME_DIR}/addons
    rm -fr ${D}/pvr.iptvsimple
}


# столкнулся с непонятной ошибкой: 
# при переопределении секции do_install, файлы, которые 
# были установлены в каталог ${D} на этапе compile удаляются, т.е. 
# на этапе запуска do_install каталог уже пуст, поэтому секцию не использую,
# возможно ошибка в немного неккоретных правилах cmake аддона, необходимо разделать стадии сборки
# make all => только сборка, make install => только установка, а в аддоне
# make all также устанавливает собранные файлы ( поэтому оставил пока так, что бы не править cmake rules )
### do_install() { ls -la ${D} }

# отключаю метод, так как после сборки файлы уже располагаются в CMAKE_INSTALL_PREFIX=${D}
do_install[noexec] = "1"



