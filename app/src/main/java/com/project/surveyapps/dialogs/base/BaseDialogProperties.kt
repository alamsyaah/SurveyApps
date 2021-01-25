package com.project.surveyapps.dialogs.base

import com.project.surveyapps.callbacks.ConnectionCallback

abstract class BaseDialogProperties(
    var cancelable: Boolean = false,
    var connectionCallback: ConnectionCallback? = null
)