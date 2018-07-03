DESCRIPTION = "First run script"
SECTION = "configs"
PR = "r1"
LICENSE = "MIT"
MD5_SUM = "md5=0835ade698e0bcf8506ecda2f7b4f302"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;${MD5_SUM}"

# секция установки скрипта первого запуска
SRC_URI = "file://first-run.sh"

inherit update-rc.d

S = "${WORKDIR}"


INITSCRIPT_NAME = "first-run.sh"
# вызов скрипта определяющего первое включение компьютера на уровень исполнения S
# вызывается после S03mountall.sh S04udev S05modutils.sh S06alignment.sh S06checkroot.sh S07bootlogd
INITSCRIPT_PARAMS = "start 07 S ."



PACKAGES = "${PN}"
FILES_${PN} = "first.bs.run /etc/init.d"

do_install_append() {
    install -d ${D}${sysconfdir}/init.d
    install -m 0755  ${WORKDIR}/first-run.sh ${D}${sysconfdir}/init.d
    # FIRST_RUN="/first.bs.run"
    touch ${D}/first.bs.run
}
