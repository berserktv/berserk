SUMMARY = "Native depends JsonSchemaBuilder for Kodi multimedia center"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"
  
require kodi-version.inc
inherit autotools gettext native

S = "${WORKDIR}/git"
JSON_SCHEMA_DIR = "${S}/tools/depends/native/JsonSchemaBuilder"


do_configure() {
    make -C ${JSON_SCHEMA_DIR}
}

do_compile() {
    cd ${JSON_SCHEMA_DIR}/native 
    make all
}

do_install() {
    cd ${JSON_SCHEMA_DIR}/native
    make install
    
    install -d ${D}${STAGING_DIR_NATIVE}${prefix_native}/bin
    install -m 0755 ${JSON_SCHEMA_DIR}/bin/* ${D}${STAGING_DIR_NATIVE}${prefix_native}/bin
}

