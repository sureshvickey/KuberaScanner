package com.kubera.scanner.percentageview.callback;


import androidx.annotation.NonNull;

public interface ProgressTextFormatter {

    @NonNull
    CharSequence provideFormattedText(float progress);

}
