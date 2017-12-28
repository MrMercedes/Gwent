package com.example.hp0331.gwent;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.btn_more)
    Button mBtnMore;
    @BindView(R.id.ll_main)
    LinearLayout mLlMain;
    private Gwent defender;
    private Gwent attacker;
    private Gwent initAttacker;
    private Gwent initDefender;
    @BindView(R.id.me_attacker_attacker)
    MaterialEditText mMeAttackerAttacker;
    @BindView(R.id.me_attacker_armor)
    MaterialEditText mMeAttackerArmor;
    @BindView(R.id.me_defender_attack)
    MaterialEditText mMeDefenderAttack;
    @BindView(R.id.me_defender_armor)
    MaterialEditText mMeDefenderArmor;
    @BindView(R.id.btn_calculate)
    Button mBtnCalculate;
    @BindView(R.id.btn_clear)
    Button mBtnClear;
    @BindView(R.id.me_attacker_attacker_left)
    TextView mMeAttackerAttackerLeft;
    @BindView(R.id.me_attacker_armor_left)
    TextView mMeAttackerArmorLeft;
    @BindView(R.id.me_defender_attack_left)
    TextView mMeDefenderAttackLeft;
    @BindView(R.id.me_defender_armor_left)
    TextView mMeDefenderArmorLeft;
    @BindView(R.id.tv_gainsevse)
    TextView mTvGainsevse;
    @BindView(R.id.tv_gainsfvse)
    TextView mTvGainsfvse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.aglais_gwent);
//        Bitmap newImg = BlurUtil.doBlur(bitmap, 5, 3);
//        Drawable drawable =new BitmapDrawable(newImg);
//        mLlMain.setBackgroundDrawable(drawable);

    }

    public void setJieGuo() {
        mMeAttackerAttackerLeft.setText(attacker.attack + "");
        mMeAttackerArmorLeft.setText(attacker.guard + "");
        mMeDefenderAttackLeft.setText(defender.attack + "");
        mMeDefenderArmorLeft.setText(defender.guard + "");
        mTvGainsevse.setText(initAttacker.attack + initDefender.attack - attacker.attack - defender.attack + "");
        System.out.println("" + initAttacker.attack + "+" + initDefender.attack + "-" + attacker.attack + "-" + defender.attack + "");

        mTvGainsfvse.setText(attacker.attack - defender.attack + initDefender.attack - initAttacker.attack + "");
        System.out.println("" + attacker.attack + "-" + defender.attack + "+" + initDefender.attack + "-" + initAttacker.attack + "");

    }

    public boolean Calculate() {
        if (mMeAttackerAttacker.getText().toString() == "" || mMeAttackerAttacker.getText().toString().equals("")) {
            Toast.makeText(MainActivity.this, R.string.toast_input_null, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (mMeDefenderAttack.getText().toString() == "" || mMeDefenderAttack.getText().toString().equals("")) {
            Toast.makeText(MainActivity.this, R.string.toast_input_null, Toast.LENGTH_SHORT).show();
            return false;
        }
        int guard1;
        int att1 = Integer.parseInt(mMeAttackerAttacker.getText().toString().trim());
        String G1 = mMeAttackerArmor.getText().toString();
        if (G1 == "" || G1.equals("")) {
            guard1 = 0;
        } else {
            guard1 = Integer.parseInt(G1.trim());
        }
        initAttacker = new Gwent(att1, guard1);
        attacker = new Gwent(att1, guard1);
        int guard2;
        int att2 = Integer.parseInt(mMeDefenderAttack.getText().toString().trim());
        String G2 = mMeDefenderArmor.getText().toString();
        if (G2 == "" || G2.equals("")) {
            guard2 = 0;
        } else {
            guard2 = Integer.parseInt(G2.trim());

        }
        initDefender = new Gwent(att2, guard2);
        defender = new Gwent(att2, guard2);
        System.out.println(initAttacker.attack + ":" + initAttacker.guard + "    " + initDefender.attack + ":" + initDefender.guard);

        while (true) {
            if (attacker.attack <= 0 || defender.attack <= 0) {
                System.out.println(attacker.attack + ":" + attacker.guard + "    " + defender.attack + ":" + defender.guard);
                return true;
            }
            defender.attacked(attacker);
            if (defender.attack <= 0 || attacker.attack <= 0) {
                System.out.println(attacker.attack + ":" + attacker.guard + "    " + defender.attack + ":" + defender.guard);
                return true;
            }
            attacker.attacked(defender);
        }
    }

    @OnClick({R.id.btn_calculate, R.id.btn_clear, R.id.btn_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_calculate:
                if (Calculate()) {
                    setJieGuo();
                }
                break;
            case R.id.btn_clear:
                mMeAttackerAttacker.setText("");
                mMeAttackerArmor.setText("");
                mMeDefenderAttack.setText("");
                mMeDefenderArmor.setText("");
                mMeAttackerAttackerLeft.setText("");
                mMeAttackerArmorLeft.setText("");
                mMeDefenderAttackLeft.setText("");
                mMeDefenderArmorLeft.setText("");
                mTvGainsevse.setText("");
                mTvGainsfvse.setText("");
                break;

            case R.id.btn_more:
                startActivity(new Intent(MainActivity.this, MoreEnemyActivity.class));
                break;
        }
    }


}
