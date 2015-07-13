package com.wonders.xlab.healthcloud.controller.steward;

import com.wonders.xlab.framework.controller.AbstractBaseController;
import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.dto.pingpp.PingDto;
import com.wonders.xlab.healthcloud.dto.result.ControllerResult;
import com.wonders.xlab.healthcloud.dto.steward.ServiceDto;
import com.wonders.xlab.healthcloud.entity.customer.User;
import com.wonders.xlab.healthcloud.entity.steward.*;
import com.wonders.xlab.healthcloud.repository.customer.UserRepository;
import com.wonders.xlab.healthcloud.repository.steward.*;
import com.wonders.xlab.healthcloud.service.pingplusplus.exception.APIConnectionException;
import com.wonders.xlab.healthcloud.service.pingplusplus.exception.APIException;
import com.wonders.xlab.healthcloud.service.pingplusplus.exception.AuthenticationException;
import com.wonders.xlab.healthcloud.service.pingplusplus.exception.InvalidRequestException;
import com.wonders.xlab.healthcloud.service.pingplusplus.model.Charge;
import com.wonders.xlab.healthcloud.service.pingpp.PingppService;
import com.wonders.xlab.healthcloud.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * Created by lixuanwu on 15/7/7.
 */
@RestController
@RequestMapping(value = "steward")
public class StewardController extends AbstractBaseController<Steward, Long> {

    @Autowired
    private StewardRepository stewardRepository;

    @Autowired
    private RecommendPackageRepository recommendPackageRepository;

    @Autowired
    private ServicesRepository servicesRepository;

    @Autowired
    private StewardOrderRepository stewardOrderRepository;

    @Autowired
    private PingppService pingppService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected MyRepository<Steward, Long> getRepository() {
        return stewardRepository;
    }

    /**
     * 获取所有的任务包
     *
     * @return
     */
    @RequestMapping(value = "getAllRecommendPackage/{address}", method = RequestMethod.GET)
    public Object getAllRecommendPackage(@PathVariable String address) {

        List<RecommendPackage> prList = recommendPackageRepository.findAll();

        boolean flag = true;
        for (RecommendPackage pr : prList) {
            if (!StringUtils.isEmpty(pr.getServiceIds())) {
                String[] strIds = pr.getServiceIds().split(",");

                Long[] serviceIds = new Long[strIds.length];
                for (int i = 0; i < strIds.length; i++)
                    serviceIds[i] = Long.parseLong(strIds[i]);
                // 查询服务，管家
                List<Services> services = this.servicesRepository.findAll(Arrays.asList(serviceIds));

                for (Services service : services) {
                    if (!address.contains("上海")) {
                        if (service.getServiceArea().equals(Services.ServiceArea.上海)) {
                            flag = false;
                            break;
                        }
                    }
                }
                if (flag == false) {
                    pr.setMessage("非常抱歉，本服务目前只在上海开通，请选择其他服务");
                    pr.setChoice(flag);
                }
            }
        }

        return new ControllerResult<List<RecommendPackage>>().setRet_code(0).setRet_values(prList).setMessage("成功！");
    }

    /**
     * 获取推荐包详情
     *
     * @param packageId
     * @return
     */
    @RequestMapping("getRecommendPackageDetail/{packageId}")
    public Object getRecommendPackageDetail(@PathVariable Long packageId) {

        RecommendPackage rp = this.recommendPackageRepository.findOne(packageId);

        if (!StringUtils.isEmpty(rp.getServiceIds())) {
            String[] strIds = rp.getServiceIds().split(",");


            Long[] serviceIds = new Long[strIds.length];
            for (int i = 0; i < strIds.length; i++)
                serviceIds[i] = Long.parseLong(strIds[i]);
            // 查询服务，管家
            List<Services> services = this.servicesRepository.findAll(Arrays.asList(serviceIds));

            Set<Services> servicesSet = new HashSet<>();
            servicesSet.addAll(services);
            rp.setServices(servicesSet);

            List<Steward> stewards = this.stewardRepository.findByRank(rp.getRank());
            System.out.println();
            int idx = (int) (System.currentTimeMillis() % stewards.size());

            rp.setSteward(stewards.get(idx));
        }

        return new ControllerResult<RecommendPackage>().setRet_code(0).setRet_values(rp).setMessage("成功！");

    }

    /**
     * 获取自定义包
     *
     * @return
     */
    @RequestMapping("listCustomPackage/{address}/{location}")
    public Object listCustomPackage(@PathVariable String address, @PathVariable String location) {

        //强制置顶
        List<Services> orderServices = this.servicesRepository.findByIsForceOrderByUsedNumberAsc(true);

        //去除强制置顶以后在用算法
        List<Services> services = this.servicesRepository.findByIsForceOrderByUsedNumberAsc(false);
        List<Services> resultServices = new ArrayList<>();
        for (Services service : services) {
            resultServices.add(service);
        }
        for (Services service : services) {
            if (!address.contains("上海")) {
                if (service.getServiceArea().equals(Services.ServiceArea.上海)) {
                    resultServices.remove(service);
                }
            }
        }

        int servicesSize = resultServices.size();
        //服务单数从使用次数高到低排列
        for (int j = servicesSize - 1, i = 0; j >= servicesSize / 2; j--, i++) {

            orderServices.add(resultServices.get(j));

            orderServices.add(resultServices.get(i));
        }

        List<Map<String, Object>> arithmeticList = new ArrayList<>();


        Map<String, Object> level1Map = new HashMap<>();
        Map<String, Object> level2Map = new HashMap<>();
        Map<String, Object> level3Map = new HashMap<>();
        Map<String, Object> level4Map = new HashMap<>();
        Map<String, Object> level5Map = new HashMap<>();

        level1Map.put("range", "0,4");
        level1Map.put("money", "0.01");

        level2Map.put("range", "5,10");
        level2Map.put("money", "28");

        level3Map.put("range", "11,17");
        level3Map.put("money", "78");

        level4Map.put("range", "18,48");
        level4Map.put("money", "158");

        level5Map.put("range", "49");
        level5Map.put("money", "298");

        arithmeticList.add(level1Map);
        arithmeticList.add(level2Map);
        arithmeticList.add(level3Map);
        arithmeticList.add(level4Map);
        arithmeticList.add(level5Map);

        List<Steward> stewards = this.stewardRepository.findByOrderByRankAsc();

        Map<String, Object> map = new HashMap<>();
        map.put("services", orderServices);
        map.put("steward", stewards);
        map.put("arithmetic", arithmeticList);

        return new ControllerResult<>().setRet_code(0).setRet_values(map).setMessage("成功");
    }

    /**
     * 查看服务详情
     *
     * @param serviceId
     * @return
     */
    @RequestMapping("serviceDetail/{serviceId}")
    public Object serviceDetail(@PathVariable Long serviceId) {

        return new ControllerResult<Services>().setRet_code(0).setRet_values(this.servicesRepository.findOne(serviceId)).setMessage("成功！");

    }

    /**
     * 查询管家详情
     *
     * @param stewardId
     * @return
     */
    @RequestMapping("stewardDetail/{stewardId}")
    public Object stewardDetail(@PathVariable Long stewardId) {

        Steward steward = stewardRepository.findOne(stewardId);

        //获取明星服务
        List<StewardOrder> stewardOrders = stewardOrderRepository.findAllBySteward(stewardId);

        if (!stewardOrders.isEmpty()) {
            List<Set<Services>> ListServices = new ArrayList<>();
            //取订单中该管家所提供过的服务
            for (StewardOrder stewardOrder : stewardOrders) {
                ListServices.add(stewardOrder.getServices());
            }

            Map<String, Integer> countServiceNumMap = new HashMap<>();

            //取出服务列表中每个服务被提供过的次数，并按value排序
            for (Set<Services> listService : ListServices) {
                for (Services services : listService) {
                    if (countServiceNumMap.containsKey(services.getServiceName())) {
                        int num = countServiceNumMap.get(services.getServiceName()) + 1;
                        countServiceNumMap.put(services.getServiceName(), num);
                    } else {
                        countServiceNumMap.put(services.getServiceName(), 1);
                    }
                }
            }

            sortMap(countServiceNumMap);

            //获取两条明星服务
            Set set = countServiceNumMap.keySet();
            Iterator it = set.iterator();
            int i = 0;
            while (it.hasNext() && (i < 2)) {
                Map<String, Object> nameMap = new HashMap();
                String key = it.next().toString();
                nameMap.put("name", key);
                nameMap.put("count", countServiceNumMap.get(key));
                steward.getStarService().add(nameMap);
                i++;
            }
        } else {
            if (null != steward) {
                //获取两条明星服务
                Map<String, Object> firstMap = new HashMap();
                Map<String, Object> secondMap = new HashMap();
                firstMap.put("name", "定期关爱");
                firstMap.put("count", 128);

                secondMap.put("name", "健康跟踪");
                secondMap.put("count", 166);
                steward.getStarService().add(firstMap);
                steward.getStarService().add(secondMap);
            } else {
                return new ControllerResult<Steward>().setRet_code(-1).setRet_values(steward).setMessage("管家不存在！");
            }
        }
        return new ControllerResult<Steward>().setRet_code(0).setRet_values(steward).setMessage("成功！");


    }

    /**
     * 计算服务费用
     *
     * @param serviceDto
     * @param result
     * @return
     */
    @RequestMapping("payServices/{userId}/{payWay}")
    public String payServices(@PathVariable Long userId, @PathVariable String payWay, @RequestBody @Valid ServiceDto serviceDto, BindingResult result) throws RuntimeException {

        if (result.hasErrors()) {

            StringBuilder builder = new StringBuilder();
            for (ObjectError error : result.getAllErrors()) {
                builder.append(error.getDefaultMessage());
            }
            new ControllerResult<String>().setRet_code(-1).setRet_values(builder.toString()).setMessage("失败");
        }

        //积分
        int integration = 0;
        //金额
        double amount = 0.00;

        String[] strIds = serviceDto.getServiceIds().split(",");
        Long[] serviceIds = new Long[strIds.length];
        for (int i = 0; i < strIds.length; i++)
            serviceIds[i] = Long.parseLong(strIds[i]);
        // 查询服务，管家
        List<Services> services = servicesRepository.findAll(Arrays.asList(serviceIds));

        Steward steward = stewardRepository.findOne(Long.parseLong(serviceDto.getStewardId()));

        User user = userRepository.findOne(userId);
        // 计算积分
        for (Services service : services)
            integration += service.getServiceIntegration();
        integration += steward.getStewardIntegration();

        // 存在推荐包
        if (serviceDto.getPackageId() != "") {
            // 获取推荐包，管家
            RecommendPackage rp = recommendPackageRepository.findOne(Long.parseLong(serviceDto.getPackageId()));

            amount = Double.parseDouble(rp.getPrice());

        } else {
            // 判断自定义积分换算金额
            if (integration >= 0 && integration <= 4) {
                amount = 0.01;
            } else if (integration >= 5 && integration <= 10) {
                amount = 28;
            } else if (integration >= 11 && integration <= 17) {
                amount = 78;
            } else if (integration >= 18 && integration <= 48) {
                amount = 158;
            }
        }

        PingDto pingDto = new PingDto("健康套餐", "健康云养生套餐", amount);

        Charge charge = pingppService.payOrder(pingDto, payWay);

        String tradeNo = "u" + userId + new Date().getTime();

        //保存订单详情
        StewardOrder stewardOrder = new StewardOrder(charge.getId(), tradeNo, amount);
        stewardOrder.setSteward(steward);
        stewardOrder.setServices(new HashSet<>(services));
        stewardOrder.setUser(user);
        this.stewardOrderRepository.save(stewardOrder);

        //服务被使用次数＋＋
        for (Services service : services) {
            service.setUsedNumber(service.getUsedNumber() + 1);
            servicesRepository.save(service);
        }
        //管家服务次数＋＋
        steward.setServicedPeriod(steward.getServicedPeriod() + 1);
        stewardRepository.save(steward);

        return charge.toString();

    }

    /**
     * 录入管家信息
     *
     * @param steward
     * @return
     */
    @RequestMapping(value = "saveSteward", method = RequestMethod.POST)
    public ControllerResult saveService(@RequestBody Steward steward) {

        stewardRepository.save(steward);

        return new ControllerResult<>().setRet_code(0).setRet_values("").setMessage("成功！");
    }

    /**
     * 获取订单号详情
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "getOrdersDetail/{userId}", method = RequestMethod.GET)
    public ControllerResult getOrdersDetail(@PathVariable Long userId) {

        List<StewardOrder> stewardOrders = stewardOrderRepository.findAllByUser(userId);

        if (!stewardOrders.isEmpty()) {

            StewardOrder stewardOrder = stewardOrders.get(0);

            int totalServicePeriod = stewardOrder.getSteward().getServicedPeriod();

            String[] detilServicedPeriod = new String[totalServicePeriod];

            for (int num = 0; num < detilServicedPeriod.length; num++) {
                detilServicedPeriod[num] = DateUtils.calculateTodayForWeek(stewardOrder.getCreatedDate(), num);
            }

            int currentServicedPeriod = DateUtils.calculateDaysOfTwoDateIgnoreHours(stewardOrder.getCreatedDate(), new Date());

            Map<String, Object> servicedPeriodMap = new HashMap<>();

            servicedPeriodMap.put("currentServicedPeriod", currentServicedPeriod);
            servicedPeriodMap.put("totalServicePeriod", totalServicePeriod);
            servicedPeriodMap.put("detailServicedPeriod", detilServicedPeriod);

            stewardOrder.setServicedPeriodStatus(servicedPeriodMap);

            return new ControllerResult<StewardOrder>().setRet_code(0).setRet_values(stewardOrder).setMessage("获取订单成功！");

        } else {

            return new ControllerResult<String>().setRet_code(-1).setRet_values("").setMessage("获取订单失败！");
        }
    }

    /**
     * 获取订单详情
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "getOrdersDetail/{userId}/{chargeId}", method = RequestMethod.GET)
    public ControllerResult getOrdersDetail(@PathVariable Long userId, @PathVariable String chargeId) throws APIException, AuthenticationException, InvalidRequestException, APIConnectionException {

        StewardOrder stewardOrder = stewardOrderRepository.findAllByChargeId(chargeId);

        if (null != stewardOrder){

            //更新付款状态
            Charge charge = pingppService.queryCharge(chargeId);

            //判断支付状态
            if (charge.getPaid()) {

                stewardOrder.setOrderStatus(StewardOrder.OrderStatus.支付成功);
            } else {
                stewardOrder.setOrderStatus(StewardOrder.OrderStatus.未支付);
            }

            int totalServicePeriod = stewardOrder.getSteward().getServicedPeriod();

            String[] detilServicedPeriod = new String[totalServicePeriod];

            for (int num = 0; num < detilServicedPeriod.length; num++) {
                detilServicedPeriod[num] = DateUtils.calculateTodayForWeek(stewardOrder.getCreatedDate(), num);
            }

            int currentServicedPeriod = DateUtils.calculateDaysOfTwoDateIgnoreHours(stewardOrder.getCreatedDate(), new Date());

            Map<String, Object> servicedPeriodMap = new HashMap<>();

            servicedPeriodMap.put("currentServicedPeriod", currentServicedPeriod);
            servicedPeriodMap.put("totalServicePeriod", totalServicePeriod);
            servicedPeriodMap.put("detailServicedPeriod", detilServicedPeriod);

            stewardOrder.setServicedPeriodStatus(servicedPeriodMap);

            return new ControllerResult<StewardOrder>().setRet_code(0).setRet_values(stewardOrder).setMessage("获取订单成功！");

        }else{

            return new ControllerResult<String>().setRet_code(-1).setRet_values("").setMessage("获取订单失败！");
        }
    }


    /**
     * 根据map的value排序
     *
     * @param oldMap
     * @return
     */
    public static Map sortMap(Map oldMap) {
        ArrayList<Map.Entry<String, Integer>> list = new ArrayList<>(oldMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {

            @Override
            public int compare(Map.Entry<String, Integer> arg0,
                               Map.Entry<String, Integer> arg1) {
                return arg0.getValue() - arg1.getValue();
            }
        });
        Map newMap = new HashMap();
        for (int i = list.size() - 1; i >= 0; i--) {
            newMap.put(list.get(i).getKey(), list.get(i).getValue());
        }
        return newMap;
    }


}
