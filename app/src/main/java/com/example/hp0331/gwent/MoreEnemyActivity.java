package com.example.hp0331.gwent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MoreEnemyActivity extends AppCompatActivity {

    @BindView(R.id.me_inputstring)
    MaterialEditText mMeInputstring;
    @BindView(R.id.max_gains)
    TextView mMaxGains;
    @BindView(R.id.attacker_attack_best)
    TextView mAttackerAttackBest;
    @BindView(R.id.Defender_defence_best)
    TextView mDefenderDefenceBest;
    @BindView(R.id.attacker_attack_best_left)
    TextView mAttackerAttackBestLeft;
    @BindView(R.id.Defender_defence_best_left)
    TextView mDefenderDefenceBestLeft;
    @BindView(R.id.btn_calculate_best)
    Button mBtnCalculateBest;
    @BindView(R.id.ll_more)
    LinearLayout mLlMore;
    @BindView(R.id.btn_clear)
    Button mBtnClear;

    private ArrayList<Gwent> arrayList;
    private ArrayList<Gains> gainsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_enemy);
        ButterKnife.bind(this);
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.webwxgetmsgimg);
//        Bitmap newImg = BlurUtil.doBlur(bitmap, 5, 3);
//        Drawable drawable =new BitmapDrawable(newImg);
//        mLlMore.setBackgroundDrawable(drawable);
    }

    public void clear() {
        mMeInputstring.setText("");
        mMaxGains.setText("");
        mAttackerAttackBest.setText("");
        mDefenderDefenceBest.setText("");
        mAttackerAttackBestLeft.setText("");
        mDefenderDefenceBestLeft.setText("");

    }

    public void compare() {
        int max = 0;
        Gains maxGains = null;
        for (int i = 0; i <= gainsArrayList.size() - 1; i++) {
            if (gainsArrayList.get(i).gain >= max) {
                max = gainsArrayList.get(i).gain;
                maxGains = gainsArrayList.get(i);
            }
        }
        if (maxGains == null) {
            Toast.makeText(MoreEnemyActivity.this, R.string.error, Toast.LENGTH_SHORT).show();

        } else {
            mMaxGains.setText(maxGains.gain + "");
            mAttackerAttackBest.setText(maxGains.attacker + "");
            mDefenderDefenceBest.setText(maxGains.defender + "");
            mAttackerAttackBestLeft.setText(maxGains.attackleft + "");
            mDefenderDefenceBestLeft.setText(maxGains.defenderleft + "");
        }
    }

    public boolean parseString() {
        String input = mMeInputstring.getText().toString();
        if (input == "" || input.equals("")) {
            Toast.makeText(MoreEnemyActivity.this, R.string.toast_input_null, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            try {
                String s = input.replace("，", ",");
                String[] ss = s.split(" ");
                arrayList = new ArrayList<>();
                for (String string : ss) {
                    Gwent gwent;
                    if (!string.contains(",")) {
                        int att = Integer.parseInt(string);
                        gwent = new Gwent(att, 0);
                    } else {
                        String[] atr = string.split("\\,");
                        int attack = Integer.parseInt(atr[0]);
                        int guard = Integer.parseInt(atr[1]);
                        gwent = new Gwent(attack, guard);
                    }
                    arrayList.add(gwent);


                }
                arrayList.size();
                if (arrayList.size() > 27) {
                    Toast.makeText(MoreEnemyActivity.this, R.string.toast_error_format, Toast.LENGTH_SHORT).show();
                    return false;
                }
                return true;
            } catch (Exception e) {
                return false;
            }

        }
    }

    public void attack() {
        gainsArrayList = new ArrayList<>();
        int maxgain = 0;
        for (int i = 0; i <= arrayList.size() - 1; i++) {
            for (int j = i + 1; j < arrayList.size(); j++) {
                Gwent gwent1 = new Gwent(arrayList.get(i).attack, arrayList.get(i).guard);
                Gwent gwent2 = new Gwent(arrayList.get(i).attack, arrayList.get(i).guard);
                Gwent gwent3 = new Gwent(arrayList.get(j).attack, arrayList.get(j).guard);
                Gwent gwent4 = new Gwent(arrayList.get(j).attack, arrayList.get(j).guard);

                int[] gain1 = Calculate(gwent1, gwent3);
                int[] gain2 = Calculate(gwent4, gwent2);
                if (gain1[0] > gain2[0]) {
                    gainsArrayList.add(new Gains(gain1[0], gain1[1], gain1[2], gain1[3], gain1[4]));
                } else if (gain1[0] < gain2[0]) {
                    gainsArrayList.add(new Gains(gain2[0], gain2[1], gain2[2], gain2[3], gain2[4]));
                } else if (gain1 == gain2) {
                    gainsArrayList.add(new Gains(gain1[0], gain1[1], gain1[2], gain1[3], gain1[4]));
                    gainsArrayList.add(new Gains(gain2[0], gain2[1], gain2[2], gain2[3], gain2[4]));

                }
            }
        }
    }

    public int[] Calculate(Gwent attacker, Gwent defender) {
        int gain1 = 0;
        Gwent initAttacker = new Gwent(attacker.attack, attacker.guard);
        Gwent initDefender = new Gwent(defender.attack, defender.guard);
        while (true) {
            if (attacker.attack <= 0 || defender.attack <= 0) {
                System.out.println(attacker.attack + ":" + attacker.guard + "    " + defender.attack + ":" + defender.guard);
                return new int[]{initAttacker.attack + initDefender.attack - attacker.attack - defender.attack,
                    initAttacker.attack,
                    initDefender.attack,
                    attacker.attack,
                    defender.attack};
            }
            defender.attacked(attacker);
            if (defender.attack <= 0 || attacker.attack <= 0) {
                System.out.println(attacker.attack + ":" + attacker.guard + "    " + defender.attack + ":" + defender.guard);
                return new int[]{initAttacker.attack + initDefender.attack - attacker.attack - defender.attack,
                    initAttacker.attack,
                    initDefender.attack,
                    attacker.attack,
                    defender.attack};
            }
            attacker.attacked(defender);
        }


    }


    @OnClick({R.id.btn_calculate_best, R.id.btn_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_calculate_best:
                if (parseString()) {
                    attack();
                    compare();
                }
                break;
            case R.id.btn_clear:
                clear();
                break;
        }
    }
}
