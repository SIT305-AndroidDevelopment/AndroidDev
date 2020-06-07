package com.example.assessment_2.model;

import android.net.Uri;

import com.example.assessment_2.R;
import com.example.assessment_2.constant.AppConstant;

import java.util.ArrayList;
import java.util.List;

public class AppDataFactory {
//    public static Bitmap bitmap = BitmapFactory.decodeResource(AssessmentApplication.getContext().getResources(), R.drawable.ic_honda);
//    public static Uri imageUri = Uri.parse(MediaStore.Images.Media.insertImage(AssessmentApplication.getContext().getContentResolver(), bitmap, null, null));

    public static List<BrandItem> getBrandList() {
        List<BrandItem> brandList = new ArrayList<>();

        BrandItem Honda = new BrandItem();
        Honda.imgRes = R.drawable.ic_honda;
        Honda.brandName = AppConstant.BRAND_HONDA;

        List<MotorItem> hondaList = new ArrayList<>();
        hondaList.add(getMotorBean("CB500X", 50, 45, 197, 500, 17, R.drawable.honda_cb500x, 72200));
        hondaList.add(getMotorBean("AfricaTwin", 99, 103, 248, 1100, 25, R.drawable.honda_africatwin, 229000));
        hondaList.add(getMotorBean("FireBlade", 217, 113, 201, 1000, 18, R.drawable.honda_fireblade, 300000));
        hondaList.add(getMotorBean("CB650R", 75, 60, 203, 650, 15, R.drawable.honda_cb650r, 105800));

        Honda.setMotoList(hondaList);

        BrandItem Yamaha = new BrandItem();
        Yamaha.imgRes = R.drawable.ic_yamaha;
        Yamaha.brandName = AppConstant.BRAND_YAMAHA;

        List<MotorItem> YamahaList = new ArrayList<>();
        YamahaList.add(getMotorBean("R1", 50, 45, 199, 1000, 17, R.drawable.yamaha_r1, 300000));
        YamahaList.add(getMotorBean("MT09", 115, 87, 193, 900, 14, R.drawable.yamaha_mt09, 129800));

        Yamaha.setMotoList(YamahaList);

        BrandItem Suzuki = new BrandItem();
        Suzuki.imgRes = R.drawable.ic_suzuki;
        Suzuki.brandName = AppConstant.BRAND_SUZUKI;

        List<MotorItem> SuzukiList = new ArrayList<>();
        SuzukiList.add(getMotorBean("V-Storm650", 50, 64, 213, 650, 20, R.drawable.suzuki_dl650, 99800));
        SuzukiList.add(getMotorBean("Hayabusa", 197, 141, 266, 1300, 21, R.drawable.suzuki_hayabusa, 300000));

        Suzuki.setMotoList(SuzukiList);

        BrandItem Kawasaki = new BrandItem();
        Kawasaki.imgRes = R.drawable.ic_kawasaki;
        Kawasaki.brandName = AppConstant.BRAND_KAWASAKI;

        List<MotorItem> KawasakiList = new ArrayList<>();
        KawasakiList.add(getMotorBean("Ninja400", 45, 37, 168, 400, 14, R.drawable.kawasaki_ninja400, 49800));
        KawasakiList.add(getMotorBean("ZX-10R", 203, 115, 208, 1000, 17, R.drawable.kawasaki_zx10r, 289000));
        KawasakiList.add(getMotorBean("Z900", 117, 95, 212, 900, 17, R.drawable.kawasaki_z900, 107900));
        KawasakiList.add(getMotorBean("H2", 228, 141, 238, 1000, 17, R.drawable.kawasaki_h2, 424000));

        Kawasaki.setMotoList(KawasakiList);

        brandList.add(Honda);
        brandList.add(Yamaha);
        brandList.add(Suzuki);
        brandList.add(Kawasaki);

        return brandList;
    }

    private static MotorItem getMotorBean(
            String bikeName,
            int horsePower,
            int torque,
            int weight,
            int displacement,
            int tankVolume,
            int picRes,
            int price) {
        MotorItem motorItem = new MotorItem();
        motorItem.setName(bikeName);
        motorItem.setHorsePower(horsePower);
        motorItem.setTorque(torque);
        motorItem.setWeight(weight);
        motorItem.setDisplacement(displacement);
        motorItem.setTankVolume(tankVolume);
        motorItem.setPicRes(picRes);
        motorItem.setPrice(price);
        return motorItem;
    }

    //113.007039,28.07643 川崎长沙
    //118.877795,31.943241 本田南京
    //113.001824,28.036646 铃木长沙
    //113.001975,28.036409 雅马哈长沙

    public static List<StoreItem> getStoreList() {
        List<StoreItem> storeList = new ArrayList<>();

        StoreItem storeHonda = new StoreItem();
        storeHonda.setStoreName(AppConstant.BRAND_HONDA);
        storeHonda.setLocationDescription("Honda NanJing");
        storeHonda.setLongitude(118.877795);
        storeHonda.setLatitude(31.943241);
        storeHonda.setStoreImage(R.drawable.ic_honda);
        storeHonda.setURL("https://motorcycles.honda.com.au/");

        StoreItem storeYamaha = new StoreItem();
        storeYamaha.setStoreName(AppConstant.BRAND_YAMAHA);
        storeYamaha.setLocationDescription("Yamaha ChangSha");
        storeYamaha.setLongitude(113.001975);
        storeYamaha.setLatitude(28.036409);
        storeYamaha.setStoreImage(R.drawable.ic_yamaha);
        storeYamaha.setURL("https://www.yamaha-motor.com.au/");

        StoreItem storeSuzuki = new StoreItem();
        storeSuzuki.setStoreName(AppConstant.BRAND_SUZUKI);
        storeSuzuki.setLocationDescription("Suzuki ChangSha");
        storeSuzuki.setLongitude(113.021824);
        storeSuzuki.setLatitude(28.036646);
        storeSuzuki.setStoreImage(R.drawable.ic_suzuki);
        storeSuzuki.setURL("https://www.suzukimotorcycles.com.au/");

        StoreItem storeKawasaki = new StoreItem();
        storeKawasaki.setStoreName(AppConstant.BRAND_KAWASAKI);
        storeKawasaki.setLocationDescription("Kawasaki ChangSha");
        storeKawasaki.setLongitude(113.007039);
        storeKawasaki.setLatitude(28.07643);
        storeKawasaki.setStoreImage(R.drawable.ic_kawasaki);
        storeKawasaki.setURL("https://kawasaki.com.au/");

        storeList.add(storeHonda);
        storeList.add(storeYamaha);
        storeList.add(storeSuzuki);
        storeList.add(storeKawasaki);

        return storeList;
    }

    public static List<AccountItem>  getAccountList() {
        List<AccountItem> accountList = new ArrayList<>();

        AccountItem admin = new AccountItem();

        admin.setUserName("admin");
        admin.setPassWord("admin");

        accountList.add(admin);

        return accountList;
    }

    public static void saveImgPath(Uri uri) {
//        imageUri = uri;
    }
}
