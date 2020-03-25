import com.github.gumtreediff.actions.ActionGenerator
import com.github.gumtreediff.actions.ActionUtil
import com.github.gumtreediff.actions.model.Action
import com.github.gumtreediff.client.Run
import com.github.gumtreediff.gen.Generators
import com.github.gumtreediff.matchers.MappingStore
import com.github.gumtreediff.matchers.Matcher
import com.github.gumtreediff.matchers.Matchers
import com.github.gumtreediff.tree.TreeContext

//todo: remove debug output from gumtree lib
fun main() {
    val srcFile = "./src/main/resources/test_1.py"
    val dstFile = "./src/main/resources/test_2.py"
    print(findActionsBetweenFiles(srcFile, dstFile))
}

fun getTreeContexts(srcFile: String, dstFile: String): Pair<TreeContext, TreeContext> {
    Run.initGenerators()
    val srcTC = Generators.getInstance().getTree(srcFile)
    val dstTC = Generators.getInstance().getTree(dstFile)
    return Pair(srcTC, dstTC)
}

fun findActionsBetweenTreeContexts(srcTC: TreeContext, dstTC: TreeContext): List<Action> {
    val m: Matcher = Matchers.getInstance().getMatcher(srcTC.root, dstTC.root)
    m.match()
    val mappingStore: MappingStore = m.getMappings()
    val g = ActionGenerator(srcTC.root, dstTC.root, mappingStore)
    return g.generate()
}

fun findActionsBetweenFiles(srcFile: String, dstFile: String): List<Action> {
    val (srcTC, dstTC) = getTreeContexts(srcFile, dstFile)
    return findActionsBetweenTreeContexts(srcTC, dstTC)
}

fun findDiffsNumber(srcFile: String, dstFile: String): Int {
    return findActionsBetweenFiles(srcFile, dstFile).size
}

//todo: find a way to get code (string) from tree context
fun applyAction(treeContext: TreeContext, action: Action): TreeContext {
    return ActionUtil.apply(treeContext, listOf(action))
}

