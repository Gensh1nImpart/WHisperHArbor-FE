package com.example.whha;

import android.os.Bundle;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.whha.utils.PublishPost;
import com.example.whha.utils.TokenTools;

public class WriteFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private EditText editText;
    private RadioGroup radioGroup;
    private Button button;
    private TextView textView;

    private String mParam1;
    private String mParam2;

    public WriteFragment() {

    }


    public static WriteFragment newInstance(String param1, String param2) {
        WriteFragment fragment = new WriteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_write, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button = getActivity().findViewById(R.id.publish_button);
        radioGroup = getActivity().findViewById(R.id.publish_radiogroup);
        editText = getActivity().findViewById(R.id.publish_edittext);
        textView = getActivity().findViewById(R.id.publish_textview);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = String.valueOf(editText.getText());
                if(text.isEmpty()){
                    textView.setText("内容不能为空!");
                    return;
                }
                int radioButtonId = radioGroup.getCheckedRadioButtonId();
                if(radioButtonId == -1){
                    textView.setText("需要选择是否公开!");
                    return;
                }else{
                    RadioButton radioButton = getActivity().findViewById(radioButtonId);
                    String buttonText = radioButton.getText().toString();
                    if(buttonText.equals("公开")){
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                if(PublishPost.Publish(text, false)){
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getContext(), "发表成功", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }else{
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getContext(), "发表失败", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        });
                        thread.start();
                    }else{
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                if(PublishPost.Publish(text, true)){
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getContext(), "发表成功", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }else{
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getContext(), "发表失败", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        });
                        thread.start();
                    }
                }
            }
        });
    }
}