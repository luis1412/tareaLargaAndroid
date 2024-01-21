package com.example.trasstarea;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.CheckBoxPreference;
import androidx.preference.EditTextPreference;
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
            CheckBoxPreference tarjetaSd = findPreference("tarjeta");
            EditTextPreference limpieza = findPreference("limpieza");
            Preference restablecer = findPreference("botonRestablecer");
            EditTextPreference nombreBd = findPreference("nombre");
            EditTextPreference ipBd = findPreference("ip");
            EditTextPreference puertoBd = findPreference("puerto");
            EditTextPreference usuarioBd = findPreference("usuario");
            EditTextPreference passBd = findPreference("contrasena");
            SwitchPreference activarBd = findPreference("bd");

            nombreBd.setEnabled(false);
            ipBd.setEnabled(false);
            ipBd.setEnabled(false);
            usuarioBd.setEnabled(false);
            puertoBd.setEnabled(false);
            passBd.setEnabled(false);


            nombreBd.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext().getApplicationContext());
                    sharedPreferences.edit().putString("nombre", (String) newValue).apply();
                    return true;
                }
            });


            activarBd.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext().getApplicationContext());
                    sharedPreferences.edit().putBoolean("bd", (boolean)newValue).apply();
                    boolean valor = (boolean) newValue;
                    nombreBd.setEnabled(valor);
                    ipBd.setEnabled(valor);
                    ipBd.setEnabled(valor);
                    usuarioBd.setEnabled(valor);
                    puertoBd.setEnabled(valor);
                    passBd.setEnabled(valor);
                    return true;
                }
            });

            restablecer.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(@NonNull Preference preference) {
                    resetearPreferencias();
                    Toast.makeText(requireContext(), "Reinicia la aplicacion para ver los cambios", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });

            //Establecemos que solo se puedan introducir numeros
            if (limpieza != null) {
                limpieza.setOnBindEditTextListener(editText -> {
                    editText.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
                });

                limpieza.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
                        String numero = (String) newValue;
                        Context applicationContext = requireContext().getApplicationContext();
                        guardarEstadoLimpieza(applicationContext, numero);
                        return true;
                    }
                });
            }

            if (tarjetaSd != null){
                tarjetaSd.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
                        boolean marcado = (boolean) newValue;
                        Context applicationContext = requireContext().getApplicationContext();
                        guardarEstadoSd(applicationContext, marcado);
                        return true;
                    }
                });

            }


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
        public void guardarEstadoLimpieza(Context context, String estado){
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            sharedPreferences.edit().putString("limpieza", estado).apply();
        }

        private void guardarEstadoTema(Context context, boolean isDarkThemeEnabled) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            sharedPreferences.edit().putBoolean("tema", isDarkThemeEnabled).apply();
        }
        private void guardarEstadoSd(Context context, boolean sdEnabled) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            sharedPreferences.edit().putBoolean("tarjeta", sdEnabled).apply();
        }

        private void guardarEstadoFuente(Context context, String estado) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            sharedPreferences.edit().putString("fuente", estado).apply();

        }

        private void resetearPreferencias(){
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());

            sharedPreferences.edit().putString("fuente", "Mediana").apply();
            sharedPreferences.edit().putBoolean("tarjeta", false).apply();
            sharedPreferences.edit().putBoolean("tema", false).apply();
            sharedPreferences.edit().putString("limpieza", "0").apply();
            sharedPreferences.edit().putString("criterio", "Alfabético").apply();
            sharedPreferences.edit().putBoolean("ordenacion", false).apply();

            getActivity().recreate();
        }

    }
}