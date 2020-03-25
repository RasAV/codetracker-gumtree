import org.junit.Test
import java.io.File
import kotlin.test.assertEquals

// it fails :(
class ActionsApplyingTest {
    @Test
    fun testActionsApplying() {
        val files = File("./src/test/resources").walkTopDown().filter { it.isFile }.map { it.absolutePath }
        for (srcFile in files) {
            for (dstFile in files) {
                val (srcTC, dstTC) = getTreeContexts(srcFile, dstFile)
                var actions = findActionsBetweenTreeContexts(srcTC, dstTC)
                while (actions.isNotEmpty()) {
                     // Get first action to apply and remove it from action list
                    val actionToApply = actions[0]
                    actions = actions.drop(1)

                    // Apply action, get new tree context from srcTc and find new actions
                    val newSrcTc = applyAction(srcTC, actionToApply)
                    val newActions = findActionsBetweenTreeContexts(newSrcTc, dstTC)

                    // Check that remaining actions and new actions are equal
                    assertEquals(actions, newActions)
                }
            }
        }
    }
}