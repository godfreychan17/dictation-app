package com.godfrey.dictationapp.activity.wordsetting

sealed class WordSettingViewCommand {
    data object ReloadWordListCommand : WordSettingViewCommand()
    data object ProcessToTextDictationActivityCommand : WordSettingViewCommand()
}
