package com.example.assessment_2.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.assessment_2.GlideImageLoaderHomeBanner;
import com.example.assessment_2.R;
import com.google.android.material.tabs.TabLayout;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    View view;
    TextView title;
    TabLayout tabLayout;
    private List<String> bannerPicList = new ArrayList();
    private List<String> tabList = new ArrayList<>();

    /**
     * 初始化顶部列表
     */ {
        tabList.add("Honda");
        tabList.add("Yamaha");
        tabList.add("Suzuki");
        tabList.add("Kawasaki");
    }

    private Banner banner;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.page_home, container, false);
        banner = view.findViewById(R.id.banner);
        tabLayout = view.findViewById(R.id.tabLayout);

        bannerPicList.add("https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=R1&step_word=&hs=0&pn=47&spn=0&di=11360&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=2&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=-1&cs=194692220%2C2288356915&os=541354621%2C857737860&simid=0%2C0&adpicid=0&lpn=0&ln=1651&fr=&fmq=1590667235939_R&fm=result&ic=undefined&s=undefined&hd=undefined&latest=undefined&copyright=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&ist=&jit=&cg=&bdtype=11&oriquery=&objurl=http%3A%2F%2Fwww.qqmtc.com%2Ffile%2Fupload%2F202004%2F20%2F095240871.jpeg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bqq4pv_z%26e3Bv54AzdH3FgjofAzdH3Ffi5o-8ld9c_z%26e3Bip4s&gsm=31&rpstart=0&rpnum=0&islist=&querylist=&force=undefined");
        bannerPicList.add("https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=1250GS&step_word=&hs=0&pn=11&spn=0&di=44330&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=2&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=-1&cs=1132345705%2C2672911690&os=525898579%2C879944611&simid=4168058607%2C447753711&adpicid=0&lpn=0&ln=715&fr=&fmq=1590667269823_R&fm=result&ic=undefined&s=undefined&hd=undefined&latest=undefined&copyright=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2Fwww.qqmtc.com%2Ffile%2Fupload%2F201812%2F18%2F142407941.jpeg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bqq4pv_z%26e3Bv54AzdH3FgjofAzdH3Ffi5o-8mnc8_z%26e3Bip4s&gsm=c&rpstart=0&rpnum=0&islist=&querylist=&force=undefined");
        bannerPicList.add("https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E7%81%AB%E5%88%83&step_word=&hs=0&pn=63&spn=0&di=66880&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=2&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=-1&cs=3961008173%2C406418048&os=2600248841%2C2023140473&simid=0%2C0&adpicid=0&lpn=0&ln=1265&fr=&fmq=1590667423234_R&fm=result&ic=undefined&s=undefined&hd=undefined&latest=undefined&copyright=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn20191105ac%2F224%2Fw640h384%2F20191105%2F2245-ihyxcrp3855856.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fh_z%26e3Bftgw_z%26e3Bv54_z%26e3BvgAzdH3Fw6ptvsj_0acndmm0l8_8w9mb9um0aa8aah5x2_z%26e3Bip4s%3Fu654%3Dw7p5&gsm=40&rpstart=0&rpnum=0&islist=&querylist=&force=undefined");
        bannerPicList.add("https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=zx10r&step_word=&hs=0&pn=3&spn=0&di=53240&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=2&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=-1&cs=1350018683%2C3597733286&os=102261747%2C1529432658&simid=3487877%2C745327868&adpicid=0&lpn=0&ln=815&fr=&fmq=1590667466526_R&fm=result&ic=undefined&s=undefined&hd=undefined&latest=undefined&copyright=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2Fpic.eyee.com%2Fupload%2Fimage%2F20151014%2F6358041487365136716338792.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3F4r_z%26e3Btpusy_z%26e3BgjpAzdH3Fw6ptvsjAzdH3Fr-8a6%25El%25la%25ln%25D8%25bc%25Ec%25Ad%25lm%25E0%25b8%25bF%25Ec%25ln%25b9%25EE%25b0%25AD_z%26e3Bip4s&gsm=4&rpstart=0&rpnum=0&islist=&querylist=&force=undefined");
        bannerPicList.add("https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=1050xt&step_word=&hs=0&pn=12&spn=0&di=4980&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=2&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=-1&cs=2624599060%2C3361934591&os=2933004827%2C1301494966&simid=0%2C0&adpicid=0&lpn=0&ln=1035&fr=&fmq=1590667504945_R&fm=result&ic=undefined&s=undefined&hd=undefined&latest=undefined&copyright=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&ist=&jit=&cg=&bdtype=11&oriquery=&objurl=http%3A%2F%2F09imgmini.eastday.com%2Fmobile%2F20200512%2F20200512213211_5253a258e8f615bb8871205a17b7b479_1.jpeg&fromurl=ippr_z2C%24qAzdH3FAzdH3F4tgt_z%26e3Bjwfp1wy_z%26e3Bv54AzdH3FwAzdH3Fdaac8dd8nd88n0b_z%26e3Bip4s&gsm=d&rpstart=0&rpnum=0&islist=&querylist=&force=undefined");
        initBanner();
        initTabLayout();

        return view;
    }

    private void initTabLayout() {
        for (int i = 0; i < tabList.size(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(tabList.get(i)));
        }
    }

    private void initBanner() {
        banner.setImageLoader(new GlideImageLoaderHomeBanner());
        banner.setImages(bannerPicList);
        banner.start();
    }

    public void Load() {
        //title.setText("当前时间：\n" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

    }

}
