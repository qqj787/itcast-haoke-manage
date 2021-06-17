package cn.itcast.haoke.dubbo.api.controller;

import cn.itcast.haoke.dubbo.api.config.MockConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("mock")
@CrossOrigin
public class MockController {
    @Autowired
    private MockConfig mockConfig;
    @GetMapping("indexMenu")
    public String indexMenu() {
        return this.mockConfig.getIndexMenu();
    }
    @GetMapping("index/info")
    public String indexInfo() {
        return this.mockConfig.getIndexInfo();
    }
    @GetMapping("index/faq")
    public String indexFaq () {
        return this.mockConfig.getIndexFaq();
    }
    @GetMapping("index/house")
    public String indexHouse() {
        return this.mockConfig.getIndexHouse();
    }
    @GetMapping("infos/list")
    public String infosList1(@RequestParam("type") Integer type) {
        switch (type) {
            case 1:
                return this.mockConfig.getInfosList1();
            case 2:
                return this.mockConfig.getInfosList2();
            case 3:
                return this.mockConfig.getInfosList3();
        }
        return this.mockConfig.getInfosList1();
    }

    @GetMapping("my/info")
    public String myInfo() {
        return this.mockConfig.getMy();
    }
}
