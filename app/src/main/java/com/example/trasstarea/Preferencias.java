package com.example.trasstarea;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;

public class Preferencias extends AppCompatActivity {

    SharedPreferences preferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        preferencias = PreferenceManager.getDefaultSharedPreferences(this);
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {


        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.preferencias, rootKey);
            ListPreference tamanoLetra = findPreference("fuente");
            SwitchPreference temaOscuro = findPreference("tema");
            ListPreference criterio = findPreference("criterio");
            SwitchPreference ordenacion = findPreference("ordenacion");

            if (ordenacion != null){
                ordenacion.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
                        boolean marcado = (boolean) newValue;
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext().getApplicationContext());
                        sharedPreferences.edit().putBoolean("ordenacion", marcado).apply();
                        return true;

                    }
                });
            }

            if (criterio != null){

                criterio.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
                        String marcado = (String) newValue;
                        Context applicationContext =  requireContext().getApplicationContext();

                        switch (marcado){
                            case "Alfabético":
                                guardarEstadoCriterio(applicationContext, "Alfabético");
                                break;
                            case "Fecha de creación":
                                guardarEstadoCriterio(applicationContext, "Fecha de creación");
                                break;
                            case "Días restantes":
                                guardarEstadoCriterio(applicationContext, "Días restantes");
                                break;
                            case "Progreso":
                                guardarEstadoCriterio(applicationContext, "Progreso");
                                break;
                        }
                        return true;
                    }
                });


            }

            if (tamanoLetra != null){
                tamanoLetra.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
                        String marcado = (String) newValue;
                        Context applicationContext = requireContext().getApplicationContext();
                        guardarEstadoFuente(applicationContext, marcado);
                        Resources rc = getResources();
                        Configuration configuration = rc.getConfiguration();
                        DisplayMetrics dm = rc.getDisplayMetrics();

                        switch (marcado) {
                            case "Pequeña":
                                configuration.fontScale = 0.5f;
                                break;
                            case "Mediana":
                                configuration.fontScale = 1f;
                                break;
                            case "Grande":
                                configuration.fontScale = 2f;
                                break;
                        }
                        rc.updateConfiguration(configuration, dm);
                        requireActivity().recreate();
                        return true;
                    }
                });

            }

            if (temaOscuro != null) {
                temaOscuro.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
                        boolean marcado = (boolean) newValue;
                        Context applicationContext = requireContext().getApplicationContext();
                        guardarEstadoTema(applicationContext, marcado);
                        if (marcado) {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        } else {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        }

                        return true;
                    }
                });
            }

        }

        public void guardarEstadoCriterio(Context context, String estado){
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            sharedPreferences.edit().putString("criterio", estado).apply();
        }

        private void guardarEstadoTema(Context context, boolean isDarkThemeEnabled) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            sharedPreferences.edit().putBoolean("tema", isDarkThemeEnabled).apply();
        }

        private void guardarEstadoFuente(Context context, String estado) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            sharedPreferences.edit().putString("fuente", estado).apply();

        }

    }
}