/*
 *  Copyright(c) 2017 lizhaotailang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.marktony.espresso.mvp.addpackage;

import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import io.github.marktony.espresso.R;
import io.github.marktony.espresso.data.source.CompaniesRepository;
import io.github.marktony.espresso.data.source.local.CompaniesLocalDataSource;
import io.github.marktony.espresso.data.source.local.PackagesLocalDataSource;
import io.github.marktony.espresso.data.source.PackagesRepository;
import io.github.marktony.espresso.data.source.remote.PackagesRemoteDataSource;

/**
 * Created by lizhaotailang on 2017/2/10.
 */

public class AddPackageActivity extends AppCompatActivity {

    private AddPackageFragment fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);

        // Set the navigation bar color
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("navigation_bar_tint", true)) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }

        if (savedInstanceState != null) {
            fragment = (AddPackageFragment) getSupportFragmentManager().getFragment(savedInstanceState, "AddPackageFragment");
        } else {
            fragment = AddPackageFragment.newInstance();
        }

        if (!fragment.isAdded()) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.view_pager, fragment, "AddPackageFragment")
                    .commit();
        }

        // Create the presenter.
        new AddPackagePresenter(PackagesRepository.getInstance(
                PackagesRemoteDataSource.getInstance(),
                PackagesLocalDataSource.getInstance()),
                CompaniesRepository.getInstance(CompaniesLocalDataSource.getInstance()),
                fragment);

    }

    // Save the fragment state to bundle.
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "AddPackageFragment", fragment);
    }

}
