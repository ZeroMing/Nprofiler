package org.nickle.nprofiler.controller;

import org.nickle.nprofiler.bean.*;
import org.nickle.nprofiler.registry.IAgentRegistry;
import org.nickle.nprofiler.service.IAgentServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

import static org.nickle.nprofiler.constant.CommonConstant.AGENT_HEART_BEAT_MAPPING;
import static org.nickle.nprofiler.constant.CommonConstant.AGENT_REGIST_MAPPING;

@RestController
@Validated
public class ManageController {
    @Autowired
    private IAgentRegistry iAgentRegistry;

    @GetMapping("/agentInfo")
    public Collection<AgentInfo> getAllAgentInfo() throws Exception {
        return iAgentRegistry.getAllAgentInfo();
    }

    @GetMapping("/jvmProcess/{id}")
    public List<JpsProcessInfo> getAllJVMProcess(@PathVariable("id") String id) throws Exception {
        IAgentServerService agentService = iAgentRegistry.getAgentService(id);
        return agentService.getJpsProcessInfo();
    }

    @GetMapping("/heapInfo/{id}/{processId}")
    public JmapHeapInfo getJmapHeapInfo(@NotBlank @PathVariable("id") String id, @NotBlank @PathVariable("processId") String processId) throws Exception {
        IAgentServerService agentService = iAgentRegistry.getAgentService(id);
        return agentService.getJmapHeapInfo(processId);
    }

    @GetMapping("/gcInfo/{id}/{processId}")
    public JstatGCInfo getJstatGCInfoo(@NotBlank @PathVariable("id") String id, @NotBlank @PathVariable("processId") String processId) throws Exception {
        IAgentServerService agentService = iAgentRegistry.getAgentService(id);
        return agentService.getJstatGCInfo(processId);
    }

    @PostMapping(AGENT_REGIST_MAPPING)
    public CommonResponse regist(@RequestBody @Validated AgentInfo agentInfo) throws Exception {
        iAgentRegistry.regist(agentInfo);
        return CommonResponse.SUCCESS;
    }

    @PutMapping(AGENT_HEART_BEAT_MAPPING)
    public CommonResponse hearbeat(@RequestBody @NotNull AgentInfo agentInfo) throws Exception {
        iAgentRegistry.checkAgentInfo(agentInfo);
        return CommonResponse.SUCCESS;
    }

}
