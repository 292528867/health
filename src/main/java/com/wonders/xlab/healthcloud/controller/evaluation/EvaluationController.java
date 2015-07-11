package com.wonders.xlab.healthcloud.controller.evaluation;

import com.wonders.xlab.framework.controller.AbstractBaseController;
import com.wonders.xlab.framework.repository.MyRepository;
import com.wonders.xlab.healthcloud.dto.evaluation.EvaluationDto;
import com.wonders.xlab.healthcloud.dto.result.ControllerResult;
import com.wonders.xlab.healthcloud.entity.evaluation.Evaluation;
import com.wonders.xlab.healthcloud.repository.evaluation.EvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by mars on 15/7/4.
 */
@RestController
@RequestMapping("evaluation")
public class EvaluationController extends AbstractBaseController<Evaluation, Long> {

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Override
    protected MyRepository<Evaluation, Long> getRepository() {
        return evaluationRepository;
    }

    /**
     * 测评列表
     * @return
     */
    @RequestMapping(value = "listEvaluation", method = RequestMethod.GET)
    private Object listEvaluation() {
        return new ControllerResult<>().setRet_code(0).setRet_values(this.evaluationRepository.findAll()).setMessage("成功");
    }

    /**
     * 添加测评主题
     * @param evaluationDto
     * @param result
     * @return
     */
    @RequestMapping(value = "addEvaluation", method = RequestMethod.POST)
    public Object addEvaluation(@RequestBody @Valid EvaluationDto evaluationDto, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder builder = new StringBuilder();
            for (ObjectError error : result.getAllErrors()) {
                builder.append(error.getDefaultMessage());
            }
            return new ControllerResult<String>().setRet_code(-1).setRet_values(builder.toString()).setMessage("失败");
        }
        this.evaluationRepository.save(evaluationDto.toNewEvalution());
        return new ControllerResult<String>().setRet_code(0).setRet_values("添加成功").setMessage("成功");
    }

    /**
     * 更新测评
     * @param evaluationId
     * @param evaluationDto
     * @param result
     * @return
     */
    @RequestMapping(value = "updateEvaluation/evaluationId", method = RequestMethod.POST)
    public Object updateEvaluation(@PathVariable Long evaluationId, @RequestBody @Valid EvaluationDto evaluationDto, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder builder = new StringBuilder();
            for (ObjectError error : result.getAllErrors()) {
                builder.append(error.getDefaultMessage());
            }
            return new ControllerResult<String>().setRet_code(-1).setRet_values(builder.toString()).setMessage("失败");
        }
        try {
            Evaluation evalution = this.evaluationRepository.findOne(evaluationId);
            if (evalution == null) {
                return new ControllerResult<String>().setRet_code(-1).setRet_values("竟然没找到").setMessage("失败");
            }
            this.evaluationRepository.save(evaluationDto.updateEvalution(evalution));
            return new ControllerResult<String>().setRet_code(0).setRet_values("更新成功").setMessage("成功");

        } catch (Exception exp) {
            exp.printStackTrace();
            return new ControllerResult<String>().setRet_code(-1).setRet_values(exp.getLocalizedMessage()).setMessage("失败");
        }
    }

//    @RequestMapping("addCompleteEvalution")
//    private Object addCompleteEvalution(@RequestBody @Valid EvaluationDto evaluationDto, BindingResult result) {
//        if (result.hasErrors()) {
//            StringBuilder builder = new StringBuilder();
//            for (ObjectError error : result.getAllErrors()) {
//                builder.append(error.getDefaultMessage());
//            }
//            return new ControllerResult<String>().setRet_code(-1).setRet_values(builder.toString()).setMessage("失败");
//        }
//        try {
//
//
//            Evalution evalution = this.evaluationRepository.findOne(1l);
//            if (evalution == null) {
//                return new ControllerResult<String>().setRet_code(-1).setRet_values("竟然没找到").setMessage("失败");
//            }
//            this.evaluationRepository.save(evaluationDto.updateEvalution(evalution));
//            return new ControllerResult<String>().setRet_code(0).setRet_values("更新成功").setMessage("成功");
//
//        } catch (Exception exp) {
//            exp.printStackTrace();
//            return new ControllerResult<String>().setRet_code(-1).setRet_values(exp.getLocalizedMessage()).setMessage("失败");
//        }
//
//    }

}
