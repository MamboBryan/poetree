package com.mambo.poetree.utils

import platform.Foundation.NSUUID

actual fun randomUUID(): String = NSUUID().UUIDString()