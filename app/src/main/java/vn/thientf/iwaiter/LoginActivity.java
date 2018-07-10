package vn.thientf.iwaiter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthProvider;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

import vn.thientf.iwaiter.Handler.HideSKeyOnFocusChange;
import vn.thientf.iwaiter.Models.User;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG ="111" ;
    private static final int OTP_LENGTH = 6;
    EditText edtPhone,edtCode;
    Button btnSignIn,btnResend;
    ProgressBar codeProgressbar,phoneProgressbar;
    TextView tvGuide,tvError;

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtPhone=findViewById(R.id.edtPhone);
        edtCode=findViewById(R.id.edtPass);
        codeProgressbar=findViewById(R.id.progressBarCode);
        phoneProgressbar=findViewById(R.id.progressBarPhone);
        tvError=findViewById(R.id.tvError);
        tvGuide=findViewById(R.id.tvGuide);
        btnSignIn=findViewById(R.id.btnSignIn);
        btnResend=findViewById(R.id.btnResend);
        //firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        //hide on click outside
        HideSKeyOnFocusChange hideSoftKey=new HideSKeyOnFocusChange(this);
        edtPhone.setOnFocusChangeListener(hideSoftKey);
        edtCode.setOnFocusChangeListener(hideSoftKey);

        //callback after send sms to phone
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                Log.d(TAG, "onVerificationCompleted:" + credential);
                phoneProgressbar.setVisibility(View.INVISIBLE);
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);
                phoneProgressbar.setVisibility(View.INVISIBLE);
                tvError.setVisibility(View.VISIBLE);
                tvError.setText(R.string.error_invalid_phone);

                // Show a message and update the UI
                // ...
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number
                //super.onCodeSent( verificationId,token);
                Toast.makeText(getApplicationContext(),"onCodeSent:" + verificationId,Toast.LENGTH_SHORT).show();
                phoneProgressbar.setVisibility(View.INVISIBLE);
                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
                //show UI for user enter OTP code
                edtCode.setVisibility(View.VISIBLE);
                btnSignIn.setEnabled(false);
                tvGuide.setText(R.string.login_instruction_enter_code);
                phoneProgressbar.setVisibility(View.INVISIBLE);
                edtPhone.setEnabled(false);
            }
        };

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendVerificationCode();
            }
        });

        edtCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String code=editable.toString();
                if (code.length()==OTP_LENGTH){
                    codeProgressbar.setVisibility(View.VISIBLE);
                    PhoneAuthCredential credential=PhoneAuthProvider.getCredential(mVerificationId,code);
                    signInWithPhoneAuthCredential(credential);
                }

            }
        });


    }

    private void sendVerificationCode() {
        phoneProgressbar.setVisibility(View.VISIBLE);
        String phone=edtPhone.getText().toString();
        PhoneAuthProvider phoneAuth=PhoneAuthProvider.getInstance();

        phoneAuth.verifyPhoneNumber(
                phone,
                120,
                TimeUnit.SECONDS,
                LoginActivity.this,
                mCallbacks
        );
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(getApplicationContext(),"Sign in success",Toast.LENGTH_LONG).show();

                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            finish();
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            tvError.setText(R.string.login_fail);
                            tvError.setVisibility(View.VISIBLE);

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                tvError.setText(R.string.error_wrong_code);
                            }
                        }
                    }
                });
    }


}
