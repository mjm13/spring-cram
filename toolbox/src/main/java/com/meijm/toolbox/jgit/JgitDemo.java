package com.meijm.toolbox.jgit;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.filter.MessageRevFilter;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.eclipse.jgit.util.RawParseUtils;

import java.io.File;

import static java.nio.charset.StandardCharsets.UTF_8;

public class JgitDemo {
    public static void main(String[] args) throws Exception {
        String projectDir = System.getProperty("user.dir");
        Git git = Git.open(new File(projectDir + "\\.git"));
        Repository repository = git.getRepository();
        Ref ref = repository.findRef("master");
        System.out.println(ref.getName());
        RevWalk walk = new RevWalk(repository);


        RevFilter messageRevFilter = MessageRevFilter.create("泛型继承相关");
        Iterable<RevCommit> all = git.log().setRevFilter(messageRevFilter).call();
        for (RevCommit revCommit : all) {
//            String result = RawParseUtils.decode(UTF_8, revCommit.getRawBuffer(), 0, revCommit.getRawBuffer().length);
            String result = RawParseUtils.decode(UTF_8, revCommit.getRawGpgSignature(), 0, revCommit.getRawGpgSignature().length);
            System.out.println(result);
//            try(TreeWalk tw = new TreeWalk(repository);) {
//                tw.setRecursive(false);
//                tw.addTree(revCommit.getTree());
//                while (tw.next()) {
//                    for (int i = 0; i < tw.getTreeCount(); i++) {
//                        String filePath = tw.getPathString();
//                        System.out.println(filePath);
//                    }
//                }
//            }
        }
    }
}
