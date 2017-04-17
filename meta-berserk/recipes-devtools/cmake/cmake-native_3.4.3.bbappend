# при сборке проекта использующего функцию cmake ExternalProject_add
# получил сообщение об ошибке:
#       unsupported protocol" log: libcurl was built with SSL disabled, https
CMAKE_EXTRACONF +=" \
    -DCMAKE_USE_OPENSSL=ON \
"
