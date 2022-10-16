package com.meijm.toolbox.jgit;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.filter.MessageRevFilter;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.eclipse.jgit.treewalk.TreeWalk;

import java.io.File;

@Slf4j
public class JgitDemo {
    public static void main(String[] args) throws Exception {
        String projectDir = System.getProperty("user.dir");
        Git git = Git.open(new File(projectDir + "\\.git"));
        Repository repository = git.getRepository();


        RevFilter messageRevFilter = MessageRevFilter.create("增加janino说明");
        Iterable<RevCommit> all = git.log().setRevFilter(messageRevFilter).call();
        for (RevCommit revCommit : all) {
            log.info("提交备注:{}",revCommit.getFullMessage());
            log.info("提交文件列表-----------------");
            try(TreeWalk tw = new TreeWalk(repository)){
                tw.setRecursive(true);
                tw.addTree(revCommit.getTree());
                while (tw.next()) {
                    int similarParents = 0;
                    for (int i = 1; i < tw.getTreeCount(); i++) {
                        if (tw.getFileMode(i) == tw.getFileMode(0) && tw.getObjectId(0).equals(tw.getObjectId(i))) {
                            similarParents++;
                        }
                        if (similarParents == 0) {
                            log.info("提交文件：{}",tw.getPathString());
                        }
                    }
                }
            }
        }
    }
}
