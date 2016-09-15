package my.prog.stas.fitnesscalculator;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class Calculate extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private ImageView male, female, clickStart;
    private SeekBar training_seekBar;
    private TextView active, mass, steady, lose_weight, proteins_result_mass, proteins_result_steady, proteins_result_lose,
                     carb_result_mass, carb_result_steady, carb_result_lose, fats_result_mass, fats_result_steady, fats_result_lose;
    private EditText age, hight, weight;
     ScrollView scrollView;

    public short Male_Female = 0;
    private float A = 0;
    private float result_male_steady = 0;
    private float result_male_mass = 0;
    private float result_male_lose = 0;
    private float result_female_mass = 0;
    private float result_female_steady = 0;
    private float result_female_lose = 0;
    private float weight_val = 0;
    private int age_val = 0;
    private int hight_val = 0;

    private int proteins_mass = 0;
    private int proteins_steady = 0;
    private int proteins_lose = 0;

    private int carb_mass = 0;
    private int carb_steady = 0;
    private int carb_lose = 0;

    private int fats_mass = 0;
    private int fats_steady = 0;
    private int fats_lose = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.iconcalculate);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setContentView(R.layout.calculate_main);

        AdView adView = (AdView)this.findViewById(R.id.AdView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        male = (ImageView) findViewById(R.id.Male);
        female = (ImageView) findViewById(R.id.Female);
        clickStart = (ImageView) findViewById(R.id.start);

        training_seekBar = (SeekBar) findViewById(R.id.training);

        scrollView = (ScrollView) findViewById(R.id.scroll);

        active = (TextView) findViewById(R.id.active);
        mass = (TextView) findViewById(R.id.mass);
        steady = (TextView) findViewById(R.id.steady);
        lose_weight = (TextView) findViewById(R.id.lose_weight);
        /* подкл к результатам */
        proteins_result_mass = (TextView) findViewById(R.id.mass_proteins_result);
        proteins_result_steady = (TextView) findViewById(R.id.steady_proteins_result);
        proteins_result_lose = (TextView) findViewById(R.id.lose_proteins_result);
        carb_result_mass = (TextView) findViewById(R.id.mass_carb_result);
        carb_result_steady = (TextView) findViewById(R.id.steady_carb_result);
        carb_result_lose = (TextView) findViewById(R.id.lose_carb_result);
        fats_result_mass = (TextView) findViewById(R.id.mass_fats_result);
        fats_result_steady = (TextView)findViewById(R.id.steady_fats_result);
        fats_result_lose = (TextView) findViewById(R.id.lose_fats_result);

        age = (EditText) findViewById(R.id.age);
        weight = (EditText) findViewById(R.id.weight);
        hight = (EditText) findViewById(R.id.hight);

        clickStart.setOnClickListener(this);

        training_seekBar.setOnSeekBarChangeListener(this);

        clickStart.setImageResource(R.drawable.effect);
    }

    @Override
    public void onClick(View v) {
        if (TextUtils.isEmpty(age.getText().toString())
                || TextUtils.isEmpty(hight.getText().toString())
                      || TextUtils.isEmpty(weight.getText().toString())
                           || Male_Female == 0
                                || weight.getText().toString().equals(".")) {
            Toast toast_error = Toast.makeText(getApplicationContext(),R.string.data,Toast.LENGTH_SHORT);
            toast_error.setGravity(Gravity.CENTER,0,100);
            toast_error.show();
            return;
        }

        weight_val = Float.parseFloat(weight.getText().toString());
        hight_val = Integer.parseInt(hight.getText().toString());
        age_val = Integer.parseInt(age.getText().toString());
        float a = Float.parseFloat(String.valueOf(training_seekBar.getProgress()));
        if (a <= 20) {
            A = 1.2f;
        }
        if (a <= 40 && a > 20) {
            A = 1.375f;
        }
        if (a <= 60 && a > 40) {
            A = 1.55f;
        }
        if (a <= 80 && a > 60) {
            A = 1.725f;
        }
        if (a <= 100 && a > 80) {
            A = 1.9f;
        }

       if(Male_Female == 1) { resultMaleMethod(); } else { resultFemaleMethod(); }

            switch (v.getId()) {
                case R.id.start:
                  if(Male_Female == 1) {
                      mass.setText((int)result_male_mass + " ккал");
                      steady.setText((int)result_male_steady + " ккал");
                      lose_weight.setText((int)result_male_lose + " ккал");

                      proteins_result_mass.setText(proteins_mass + " ккал  | " + proteins_mass/4 + " гр");
                      proteins_result_steady.setText(proteins_steady + " ккал  | " + proteins_steady/4 + " гр");
                      proteins_result_lose.setText(proteins_lose + " ккал  | " + proteins_lose/4 + " гр");

                      carb_result_mass.setText(carb_mass + " ккал  | " + carb_mass/4 + " гр");
                      carb_result_steady.setText(carb_steady + " ккал  | " + carb_steady/4 + " гр");
                      carb_result_lose.setText(carb_lose + " ккал  | " + carb_lose/4 + " гр");

                      fats_result_mass.setText(fats_mass + " ккал  | " + fats_mass/9 + " гр");
                      fats_result_steady.setText(fats_steady + " ккал  | " + fats_steady/9 + " гр");
                      fats_result_lose.setText(fats_lose + " ккал  | " + fats_lose/9 + " гр");
                  } else {

                      proteins_result_mass.setText(proteins_mass + " ккал  | " + proteins_mass/4 + " гр");
                      proteins_result_steady.setText(proteins_steady + " ккал  | " + proteins_steady/4 + " гр");
                      proteins_result_lose.setText(proteins_lose + " ккал  | " + proteins_lose/4 + " гр");

                      carb_result_mass.setText(carb_mass + " ккал  | " + carb_mass/4 + " гр");
                      carb_result_steady.setText(carb_steady + " ккал  | " + carb_steady/4 + " гр");
                      carb_result_lose.setText(carb_lose + " ккал  | " + carb_lose/4 + " гр");

                      fats_result_mass.setText(fats_mass + " ккал  | " + fats_mass/9 + " гр");
                      fats_result_steady.setText(fats_steady + " ккал  | " + fats_steady/9 + " гр");
                      fats_result_lose.setText(fats_lose + " ккал  | " + fats_lose/9 + " гр");

                      mass.setText((int)result_female_mass + " ккал" );
                      steady.setText((int)result_female_steady + " ккал");
                      lose_weight.setText((int)result_female_lose + " ккал");
                  }
                    break;
            }
        Toast toast_ready = Toast.makeText(getApplicationContext(),"готово",Toast.LENGTH_SHORT);
        toast_ready.setGravity(Gravity.CENTER,0,100);
        toast_ready.show();
    }

    private void resultMaleMethod() {

        result_male_steady = (int) ((10 * weight_val + 6.25f * hight_val - 5 * age_val + 5) * A);
        result_male_lose = (int) ((10 * weight_val + 6.25f * hight_val - 5 * age_val + 5) * A) - 503;
        result_male_mass = (int) ((10 * weight_val + 6.25f * hight_val - 5 * age_val + 5) * A) + 503;

        proteins_mass = (int)(2 * weight_val) * 4;
        proteins_steady = (int)(2 * weight_val) * 4;
        proteins_lose = (int)(2 * weight_val) * 4;

        fats_mass = (int) weight_val * 9;
        fats_steady = (int) weight_val * 9;
        fats_lose = (int) weight_val * 9;

        carb_mass = (int)result_male_mass - (proteins_mass + fats_mass);
        carb_steady = (int)result_male_steady - (proteins_steady + fats_steady);
        carb_lose = (int) result_male_lose - (proteins_lose + fats_lose);

    }

    private void resultFemaleMethod() {

        result_female_steady = (int) ((10 * weight_val + 6.25f * hight_val - 5 * age_val - 161) * A);
        result_female_lose = (int) ((10 * weight_val + 6.25f * hight_val - 5 * age_val - 161) * A) - 500;
        result_female_mass = (int) ((10 * weight_val + 6.25f * hight_val - 5 * age_val - 161) * A) + 500;

        proteins_mass = (int)(2 * weight_val) * 4;
        proteins_steady = (int)(2 * weight_val) * 4;
        proteins_lose = (int)(2 * weight_val) * 4;

        fats_mass = (int) weight_val * 9;
        fats_steady = (int) weight_val * 9;
        fats_lose = (int) weight_val * 9;

        carb_mass = (int)result_female_mass - (proteins_mass + fats_mass);
        carb_steady = (int)result_female_steady - (proteins_steady + fats_steady); // тут
        carb_lose = (int) result_female_lose - (proteins_lose + fats_lose);

    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int val = Integer.valueOf(seekBar.getProgress());
        if (val <= 20) {
            active.setTextColor(Color.parseColor("#7FFF0000"));
            active.setText(R.string.active_1);
        }
        if (val <= 40 && val > 20) {
            active.setTextColor(Color.parseColor("#a4ff0000"));
            active.setText(R.string.active_2);
        }
        if (val <= 60 && val > 40) {
            active.setTextColor(Color.parseColor("#baff0000"));
            active.setText(R.string.active_3);
        }
        if (val <= 80 && val > 60) {
            active.setTextColor(Color.parseColor("#d8ff0000"));
            active.setText(R.string.active_4);
        }
        if (val <= 100 && val > 80) {
            active.setTextColor(Color.parseColor("#ff0000"));
            active.setText(R.string.active_5);
        }


    }

    public void clickMale(View v) {
        Toast toast = Toast.makeText(getApplicationContext(),
                R.string.toast_male, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 100);
        toast.show();
        Male_Female = 1;
        male.setImageResource(R.drawable.man_green);
        female.setImageResource(R.drawable.woman1);
    }

    public void clickFemale(View v) {
        Male_Female = 2;
        female.setImageResource(R.drawable.womangreen);
        male.setImageResource(R.drawable.man1);
        Toast toast2 = Toast.makeText(getApplicationContext(),
                R.string.toast_female, Toast.LENGTH_SHORT);
        toast2.setGravity(Gravity.CENTER, 0, 100);
        toast2.show();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
