/*
 * Copyright 2013 Jake Wharton
 * Copyright 2014 Prateek Srivastava (@f2prateek)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.f2prateek.dart.example;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;
import com.f2prateek.dart.Optional;
import org.parceler.Parcels;

public class SampleActivity extends Activity {
    public static final String DEFAULT_EXTRA_VALUE = "a default value";

    private static final String EXTRA_STRING = "ExtraString";
    private static final String EXTRA_INT = "ExtraInt";
    private static final String EXTRA_PARCELABLE = "ExtraParcelable";
    private static final String EXTRA_OPTIONAL = "ExtraOptional";
    private static final String EXTRA_PARCEL = "ExtraParcel";
    private static final String EXTRA_WITH_DEFAULT = "ExtraWithDefault";

    @InjectExtra(EXTRA_STRING) String stringExtra;
    @InjectExtra(EXTRA_INT) int intExtra;
    @InjectExtra(EXTRA_PARCELABLE) ComplexParcelable parcelableExtra;
    @InjectExtra(EXTRA_PARCEL) ExampleParcel parcelExtra;
    @butterknife.Optional @InjectExtra(EXTRA_OPTIONAL) String optionalExtra;
    @Optional @InjectExtra(EXTRA_WITH_DEFAULT) String defaultExtra = DEFAULT_EXTRA_VALUE;

    @InjectView(R.id.string_extra) TextView stringExtraTextView;
    @InjectView(R.id.int_extra) TextView intExtraTextView;
    @InjectView(R.id.parcelable_extra) TextView parcelableExtraTextView;
    @InjectView(R.id.optional_extra) TextView optionalExtraTextView;
    @InjectView(R.id.parcel_extra) TextView parcelExtraTextView;
    @InjectView(R.id.default_extra) TextView defaultExtraTextView;

    public static Intent getLaunchIntent(Context context,
                                         String string,
                                         int anInt,
                                         ComplexParcelable complexParcelable,
                                         ExampleParcel exampleParcel) {
        Intent intent = new Intent(context, SampleActivity.class);
        intent.putExtra(SampleActivity.EXTRA_STRING, string);
        intent.putExtra(SampleActivity.EXTRA_INT, anInt);
        intent.putExtra(SampleActivity.EXTRA_PARCELABLE, complexParcelable);
        intent.putExtra(SampleActivity.EXTRA_PARCEL, Parcels.wrap(exampleParcel));
        return intent;
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        ButterKnife.inject(this);
        Dart.inject(this);

        // Contrived code to use the "injected" extras.
        stringExtraTextView.setText(stringExtra);
        intExtraTextView.setText(String.valueOf(intExtra));
        parcelableExtraTextView.setText(String.valueOf(parcelableExtra));
        optionalExtraTextView.setText(String.valueOf(optionalExtra));
        parcelExtraTextView.setText(String.valueOf(parcelExtra.getName()));
        defaultExtraTextView.setText(String.valueOf(defaultExtra));
    }
}
