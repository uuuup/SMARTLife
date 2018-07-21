package com.example.uuuup.myapplication.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMapOptions;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.overlay.WalkRouteOverlay;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;

import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RouteSearch;

import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.view.OptionsPickerView;

import com.example.uuuup.myapplication.MoreInformation;
import com.example.uuuup.myapplication.R;
import com.example.uuuup.myapplication.ToastUtil;
import com.example.uuuup.myapplication.bean.CardBean;

import com.bigkoo.pickerview.listener.OnOptionsSelectListener;

import scut.carson_ho.searchview.ICallBack;
import scut.carson_ho.searchview.SearchView;
import scut.carson_ho.searchview.bCallBack;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FragmentOne extends Fragment implements  LocationSource, AMapLocationListener, PoiSearch.OnPoiSearchListener,
        AMap.OnMarkerClickListener, AMap.OnMapClickListener, AMap.InfoWindowAdapter, AMap.OnInfoWindowClickListener,
        View.OnClickListener, RouteSearch.OnRouteSearchListener {
    private View view;
    private MapView mMapView;//地图容器
    private boolean isFirstLoc = true;//标识，用于判断是否只显示一次定位信息和用户重新定位

    public AMapLocationClient mLocationClient = null;//声明AMapLocationClient类对象
    public AMapLocationClientOption mLocationOption = null;//声明AMapLocationClientOption对象，实际是关于定位的参数

    private double lat = 45.7784237183;//经纬度 默认为天安门39.9088691069,116.3973823161
    private double lon = 126.6177728296;

    private AMap aMap;//地图类
    private float zoomlevel = 17f; //地图放大级别

    private LocationSource.OnLocationChangedListener mListener = null;//声明mListener对象，定位监听器
    private Marker oldMarker;//点击的marker
    private LatLng myLatLng;//我的位置

    //显示路径
    private RouteSearch mRouteSearch;
    private WalkRouteResult mWalkRouteResult;
    private RelativeLayout mBottomLayout, mHeadLayout;
    private ProgressDialog progDialog = null;// 搜索时进度条

    private Button but_go;
    private SearchView searchView;
    private OptionsPickerView pvCustomOptions;
    private ArrayList<CardBean> cardItem = new ArrayList<>();

    private ArrayList<PoiItem> pois;
    private ArrayList<Marker> markers = new ArrayList<>();
    private int Oldoptions1;
    private boolean isData = false;

    private SharedPreferences pref;//本地存储数据
    private SharedPreferences.Editor editor;


    public static FragmentOne newInstance() {
        FragmentOne fragment = new FragmentOne();
        return fragment;
    }

    public FragmentOne() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        view = inflater.inflate(R.layout.fragment_one, container, false);
        initview(savedInstanceState, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        pref = PreferenceManager.getDefaultSharedPreferences(getContext());

        searchView = (SearchView) getView().findViewById(R.id.searchview);// 4. 设置点击搜索按键后的操作（通过回调接口）
        searchView.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String string) {
                Intent intent = new Intent(getActivity(), TextBusActivity.class);
                intent.putExtra("bus", string);
                startActivity(intent);
            }
        });

        // 5. 设置点击返回按键后的操作（通过回调接口）
        searchView.setOnClickBack(new bCallBack() {
            @Override
            public void BackAciton() {
            }
        });
    }

    public void initview(Bundle savedInstanceState, View view) {
        mMapView = (MapView) view.findViewById(R.id.fragment_one_map);
        mMapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mMapView.getMap();
            aMap.setLocationSource(this);//设置了定位的监听,这里要实现LocationSource接口
            aMap.setMyLocationEnabled(true);//显示定位层并且可以触发定位,默认是flase


            aMap.moveCamera(CameraUpdateFactory.zoomTo(zoomlevel));
            aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
                @Override
                public void onCameraChange(CameraPosition cameraPosition) {
                }
                @Override
                public void onCameraChangeFinish(CameraPosition cameraPosition) {
                    if (zoomlevel != cameraPosition.zoom ){
                        zoomlevel = cameraPosition.zoom;
                        //Toast.makeText(getContext(),"缩放了，不作为",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            UiSettings settings = aMap.getUiSettings();//设置显示定位按钮 并且可以点击
            settings.setMyLocationButtonEnabled(false);// 是否显示定位按钮

            settings.setZoomControlsEnabled(true);//管理缩放控件
            settings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_LEFT);//设置logo位置，左下，底部居中，右下
            settings.setScaleControlsEnabled(true);//设置显示地图的默认比例尺
            //添加指南针
            aMap.getCameraPosition(); //方法可以获取地图的旋转角度
            settings.setCompassEnabled(true);
        }
        //初始化对象
        mRouteSearch = new RouteSearch(getContext());
        mRouteSearch.setRouteSearchListener(this);
        //开始定位
        location();

        but_go = (Button) view.findViewById(R.id.btn_CustomOptions);
        but_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pvCustomOptions.show();
            }
        });

    }

    private void location() {
        mLocationClient = new AMapLocationClient(getContext());//初始化定位
        mLocationClient.setLocationListener(this);//设置定位回调监听

        mLocationOption = new AMapLocationClientOption();//初始化定位参数
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//设置定位模式为Hight_Accuracy高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式

        mLocationOption.setNeedAddress(true);//设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setOnceLocation(false);//设置是否只定位一次,默认为false
        mLocationOption.setWifiActiveScan(true);//设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setMockEnable(true);//设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setInterval(2000);//设置定位间隔,单位毫秒,默认为2000ms

        mLocationClient.setLocationOption(mLocationOption);//给定位客户端对象设置定位参数
        mLocationClient.startLocation();//启动定位
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mMapView.onDestroy();
        mLocationClient.stopLocation();//停止定位
        mLocationClient.onDestroy();//销毁定位客户端
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        if (mLocationClient != null) {
            mLocationClient.onDestroy();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
        mMapView.onSaveInstanceState(outState);
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //可在其中解析amapLocation获取相应内容
                aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                lat = aMapLocation.getLatitude();//获取纬度
                lon = aMapLocation.getLongitude();//获取经度
                aMapLocation.getAccuracy();//获取精度信息
                aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                aMapLocation.getCountry();//国家信息
                aMapLocation.getProvince();//省信息
                aMapLocation.getCity();//城市信息
                aMapLocation.getDistrict();//城区信息
                aMapLocation.getStreet();//街道信息
                aMapLocation.getStreetNum();//街道门牌号信息
                aMapLocation.getCityCode();//城市编码
                aMapLocation.getAdCode();//地区编码
                aMapLocation.getAoiName();//获取当前定位点的AOI信息
                lat = 45.75000;
                lon = 126.63333;
                myLatLng = new LatLng(lat, lon);
                editor = pref.edit();
                editor.putString("city", aMapLocation.getCity());//如果复选框被选中则进行提交


                //获取定位时间
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(aMapLocation.getTime());
                df.format(date);//定位时间

                poiSearch(lat, lon);//传入此时定位的经纬度，进行搜索公交站点

                // 如果不设置标志位，此时再拖动地图时，它会不断将地图移动到当前的位置
                if (isFirstLoc) {
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(zoomlevel));//设置缩放级别
                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude())));//将地图移动到定位点
                    mListener.onLocationChanged(aMapLocation);//点击定位按钮 能够将地图的中心移动到定位点

                    StringBuffer buffer = new StringBuffer();//获取定位信息
                    buffer.append(aMapLocation.getCountry() + ""
                            + aMapLocation.getProvince() + ""
                            + aMapLocation.getCity() + ""
                            + aMapLocation.getProvince() + ""
                            + aMapLocation.getDistrict() + ""
                            + aMapLocation.getStreet() + ""
                            + aMapLocation.getStreetNum());
                    Toast.makeText(getContext(), buffer.toString(), Toast.LENGTH_LONG).show();
                    isFirstLoc = false;
                }

            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("地图错误", "定位失败, 错误码:" + aMapLocation.getErrorCode() + ", 错误信息:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
    }


    public void poiSearch(double lat, double lon) {
        PoiSearch.Query query = new PoiSearch.Query("公交站点", "", "");//"150702"为公交站点的poi
        query.setPageSize(20);
        PoiSearch search = new PoiSearch(getContext(), query);
        search.setBound(new PoiSearch.SearchBound(new LatLonPoint(lat, lon), 10000));//哈尔滨的经纬度是45.7784237183, 126.6177728296
        search.setOnPoiSearchListener(this);
        search.searchPOIAsyn();
        //query设置的范围“哈尔滨”需要跟setBound的范围一致, query的第三个参数不设置也可以, 跟设置成“哈尔滨”一致
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        markers.clear();
        pois = poiResult.getPois();
        for (PoiItem poi : pois) {
            //获取经纬度对象
            LatLonPoint llp = poi.getLatLonPoint();
            double latOfPoi = llp.getLatitude();
            double lonOfPoi = llp.getLongitude();
            //获取标题
            String title = poi.getTitle();
            //获取内容
            String snippet = poi.getSnippet();
            //cardItem.add(new CardBean(0, title));

            LatLng latLng = new LatLng(latOfPoi, lonOfPoi);
            MarkerOptions markerOptions = new MarkerOptions().anchor(0.5f, 0.5f)
                    .position(latLng)
                    .title(title)
                    .snippet(snippet)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_normal));

            Marker marker = aMap.addMarker(markerOptions);

            markers.add(marker);

            aMap.setOnMarkerClickListener(this);
            aMap.setInfoWindowAdapter(this);//AMap类中
            aMap.setOnInfoWindowClickListener(this);
        }
        if(!isData){
            getOptionData();
            initCustomOptionPicker();
            isData = true;
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {
    }


    //地图的点击事件
    @Override
    public void onMapClick(LatLng latLng) {
        //点击地图上没marker 的地方，隐藏inforwindow
        if (oldMarker != null) {
            oldMarker.hideInfoWindow();
            oldMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_normal));
        }
    }

    //maker的点击事件
    @Override
    public boolean onMarkerClick(Marker marker) {
        if (!marker.getPosition().equals(myLatLng)) { //点击的marker不是自己位置的那个marker
            if (oldMarker != null) {
                oldMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_normal));
            }
            marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_selected));
        }
        oldMarker = marker;
        marker.showInfoWindow();
        return false; //返回 “false”，除定义的操作之外，默认操作也将会被执行
    }


    //从这里开始是自定义显示InfoWindow
    @Override
    public View getInfoWindow(Marker marker) {
        LatLng latLng;
        LinearLayout more;
        LinearLayout navigation;
        TextView nameTV;
        String Name;
        TextView addrTV;
        String snippet;

        latLng = marker.getPosition();
        Name = marker.getTitle();
        snippet = marker.getSnippet();
        final String[] list = snippet.split(";");//提取出每个站点

        View view = getLayoutInflater(getArguments()).inflate(R.layout.view_infowindow, null);//view_infowindow为自定义layout文件
        navigation = (LinearLayout) view.findViewById(R.id.navigation_LL);
        more = (LinearLayout) view.findViewById(R.id.call_LL);
        nameTV = (TextView) view.findViewById(R.id.agent_name);
        addrTV = (TextView) view.findViewById(R.id.agent_addr);

        nameTV.setText(Name);
        addrTV.setText(list[0]);//首页只显示第一个站点
        navigation.setOnClickListener(this);
        more.setOnClickListener(this);
        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    //InfoWindow的点击事件
    @Override
    public void onInfoWindowClick(Marker marker) {
    }

    //自定义InfoWindow中有两个按钮，这是它们的点击事件
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.navigation_LL:  //点击导航
                LatLng latLng = oldMarker.getPosition();
                RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(new LatLonPoint(lat, lon),
                        new LatLonPoint(latLng.latitude, latLng.longitude));
                RouteSearch.WalkRouteQuery query = new RouteSearch.WalkRouteQuery(fromAndTo, 3);//1代表步行，0是驾车，2是骑行
                mRouteSearch.calculateWalkRouteAsyn(query);//开始算路
                break;

            case R.id.call_LL:  //出现更多
                Intent intent = new Intent(getActivity(), MoreInformation.class);
                intent.putExtra("more_information", oldMarker.getSnippet());
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {

    }


    /**
     * 隐藏进度框
     */
    private void dissmissProgressDialog() {
        if (progDialog != null) {
            progDialog.dismiss();
        }
    }


    @Override
    public void onWalkRouteSearched(WalkRouteResult result, int i) {
        dissmissProgressDialog();
        aMap.clear();// 清理地图上的所有覆盖物
        if (i == 1000) {
            if (result != null && result.getPaths() != null) {
                if (result.getPaths().size() > 0) {
                    mWalkRouteResult = result;
                    final WalkPath walkPath = mWalkRouteResult.getPaths()
                            .get(0);
                    WalkRouteOverlay walkRouteOverlay = new WalkRouteOverlay(
                            getContext(), aMap, walkPath,
                            mWalkRouteResult.getStartPos(),
                            mWalkRouteResult.getTargetPos());
                    walkRouteOverlay.removeFromMap();
                    walkRouteOverlay.addToMap();
                    walkRouteOverlay.zoomToSpan();
                    mBottomLayout.setVisibility(View.VISIBLE);
                } else if (result != null && result.getPaths() == null) {
                    Toast.makeText(getContext(), "no result", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "no result", Toast.LENGTH_SHORT).show();
            }
        } else {
            ToastUtil.showerror(getContext(), i);
        }
    }

    private void initCustomOptionPicker() {//条件选择器初始化，自定义布局
        pvCustomOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                int i = options1;
                String tx = cardItem.get(i).getPickerViewText();
                Oldoptions1 = i;
                but_go.setText(tx);

            }
        }).setLayoutRes(R.layout.pickerview_custom_options, new CustomListener() {
            @Override
            public void customLayout(View v) {
                final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                tvSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pvCustomOptions.returnData();
                        //Toast.makeText(getContext(), Oldoptions1, Toast.LENGTH_SHORT).show();
                        //onMapClick(new LatLng(markers.get(Oldoptions1).getPosition().latitude,markers.get(Oldoptions1).getPosition().longitude));
                        aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng( markers.get(Oldoptions1).getPosition().latitude, markers.get(Oldoptions1).getPosition().longitude)));
                        onMarkerClick(markers.get(Oldoptions1));
                        pvCustomOptions.dismiss();
                    }
                });

                ivCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(getContext(), "hello", Toast.LENGTH_SHORT).show();
                        pvCustomOptions.dismiss();
                    }
                });
            }
        })
                .isDialog(true)
                .build();
        pvCustomOptions.setPicker(cardItem);//添加数据
    }

    private void getOptionData() {
        for (int i = 0; i < pois.size(); i++) {
            cardItem.add(new CardBean(i, pois.get(i).getTitle()));
        }
    }
}