/**
 * 
 * APDPlat - Application Product Development Platform
 * Copyright (c) 2013, 杨尚川, yang-shangchuan@qq.com
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package org.apdplat.evaluation.impl;

import edu.fudan.nlp.cn.tag.CWSTagger;
import java.util.ArrayList;
import java.util.List;
import org.apdplat.evaluation.Evaluation;
import org.apdplat.evaluation.EvaluationResult;
import org.apdplat.evaluation.Segmenter;

/**
 * FudanNLP分词器分词效果评估
 * @author 杨尚川
 */
public class FudanNLPEvaluation extends Evaluation{
    @Override
    public List<EvaluationResult> run() throws Exception {
        List<EvaluationResult> list = new ArrayList<>();
        
        CWSTagger tag = new CWSTagger("lib/fudannlp_seg.m");
        tag.setEnFilter(true);
        
        System.out.println("开始评估 FudanNLP");
        list.add(run(tag));
        
        Evaluation.generateReport(list, "FudanNLP分词器分词效果评估报告.txt");
        
        return list;
    }
    private EvaluationResult run(final CWSTagger tagger) throws Exception{
        // 对文本进行分词
        String resultText = "temp/result-text-FudanNLP.txt";
        float rate = segFile(testText, resultText, new Segmenter(){
            @Override
            public String seg(String text) {
                return tagger.tag(text);                
            }
        });
        // 对分词结果进行评估
        EvaluationResult result = evaluate(resultText, standardText);
        result.setAnalyzer("FudanNLP");
        result.setSegSpeed(rate);
        return result;
    }
    public static void main(String[] args) throws Exception{
        Evaluation.generateReport(new FudanNLPEvaluation().run(), "FudanNLP分词器分词效果评估报告.txt");
    }
}