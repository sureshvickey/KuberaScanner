package com.kubera.scanner.percentageview.callback;


import android.support.annotation.NonNull;

public interface ProgressTextFormatter {

    @NonNull
    CharSequence provideFormattedText(float progress);

}
