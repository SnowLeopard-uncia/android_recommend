package com.zyw.recommend_system.ui.upload;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.zyw.recommend_system.R;
import com.zyw.recommend_system.databinding.ActivityCollectionBinding;
import com.zyw.recommend_system.databinding.ActivityUploadBinding;
import com.zyw.recommend_system.ui.BaseActivity;
import com.zyw.recommend_system.ui.collection.CollectionViewModel;
import com.zyw.recommend_system.ui.real_name.RealNameActivity;
import com.zyw.recommend_system.utils.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UploadActivity extends BaseActivity {

    ActivityUploadBinding activityUploadBinding;

    UploadLoadViewModel uploadLoadViewModel;
    String selectTime ="0";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityUploadBinding = DataBindingUtil.setContentView(this, R.layout.activity_upload);
        uploadLoadViewModel = new ViewModelProvider(this).get(UploadLoadViewModel.class);
        initNavBar();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.classifyArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activityUploadBinding.spClassify.setAdapter(adapter);

        activityUploadBinding.spClassify.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                activityUploadBinding.spClassify.setSelection(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                activityUploadBinding.spClassify.setSelection(0);
            }
        });

        activityUploadBinding.btnUploadBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        activityUploadBinding.btnUploadArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = activityUploadBinding.etTitle.getText().toString();
                String author = activityUploadBinding.etAuthor.getText().toString();
                String summary = activityUploadBinding.etSummary.getText().toString();
                String keyWord = activityUploadBinding.etKeyword.getText().toString();
                String link = activityUploadBinding.etLink.getText().toString();
                String classify = activityUploadBinding.spClassify.getSelectedItem().toString();

                if (title.equals("")){
                    Toast.makeText(UploadActivity.this,"请填写标题",Toast.LENGTH_SHORT).show();
                }else if (author.equals("")){
                    Toast.makeText(UploadActivity.this,"请填写作者",Toast.LENGTH_SHORT).show();
                }else if (summary.equals("")){
                    Toast.makeText(UploadActivity.this,"请填写摘要",Toast.LENGTH_SHORT).show();
                }
                else if (keyWord.equals("")){
                    Toast.makeText(UploadActivity.this,"请填写关键词",Toast.LENGTH_SHORT).show();
                }
                else if (link.equals("")){
                    Toast.makeText(UploadActivity.this,"请填写链接",Toast.LENGTH_SHORT).show();
                }else if (selectTime.equals("0")){
                    Toast.makeText(UploadActivity.this,"请选择时间",Toast.LENGTH_SHORT).show();
                }else{
                    uploadLoadViewModel.postArticleState(title,author,summary,keyWord,link,classify,selectTime).observe(UploadActivity.this, new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean aBoolean) {
                            if (aBoolean){
                                activityUploadBinding.uploadArticleArea.setVisibility(View.GONE);
                                activityUploadBinding.uploadArticleSuccess.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                }
            }
        });

        activityUploadBinding.btnChooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showYearMonthDayPicker();
            }
        });

    }

    private void showYearMonthDayPicker() {
        Calendar calendar = Calendar.getInstance();


        TimeUtils.showDatePickerDialog(this, TimeUtils.THEME_HOLO_LIGHT, "请选择年月日",  calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH), new TimeUtils.OnDatePickerListener() {
            @Override
            public void onConfirm(int year, int month, int dayOfMonth) {
                String monthString = String.valueOf(month);
                String dayOfMonthString = String.valueOf(dayOfMonth);
                if (month<10){

                    monthString = "0"+monthString;
                }
                if (dayOfMonth<10){

                    dayOfMonthString = "0"+dayOfMonthString;
                }
                selectTime = year + "-" + monthString + "-" + dayOfMonthString;

                Toast.makeText(UploadActivity.this,year + "-" + month + "-" + dayOfMonth,Toast.LENGTH_SHORT).show();
                activityUploadBinding.btnChooseTime.setText(selectTime);
            }

            @Override
            public void onCancel() {
                Toast.makeText(UploadActivity.this,"取消选择",Toast.LENGTH_SHORT).show();
            }
        });
    }

}