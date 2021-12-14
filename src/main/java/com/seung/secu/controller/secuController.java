package com.seung.secu.controller;

import com.seung.secu.dto.STORE;
import com.seung.secu.dto.secuDTO;
import com.seung.secu.service.secuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class secuController {

    private ModelAndView mav = new ModelAndView();

    @Autowired
    private secuService svc;

    // 초기 실행화면
    @RequestMapping("/")
    public String index(){
        return "index";
    }

    // index로 이동시키기
    @RequestMapping(value = "/index")
    public String index1(){
        return "index";
    }

    // join : security 사용하기 예시
    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public ModelAndView join(@ModelAttribute secuDTO secu){
        mav = svc.secuJoin(secu);
        return mav;
    }

    // login : security 사용하기 예시
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(@ModelAttribute secuDTO secu){
        mav = svc.secuLogin(secu);
        return mav;
    }

    // dmap1 페이지로 이동
    @RequestMapping("/dmap1")
    public String dmap1(){
        return "dmap1";
    }

    // dmap2 페이지로 이동
    @RequestMapping("/dmap2")
    public String dmap2(){
        return "dmap2";
    }

    // dmap3 페이지로 이동
    @RequestMapping("/dmap3")
    public ModelAndView dmap3(@ModelAttribute STORE store){
        mav = svc.dmap3(store);
        return mav;
    }


}
