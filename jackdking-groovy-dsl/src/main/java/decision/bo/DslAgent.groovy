package dsl.groovy

import dsl.groovy.delegate.HttpDelegate
import dsl.groovy.delegate.ParamDelegate
import groovy.util.logging.Slf4j

/**
 * @author lesofn
 * @version 1.0 2021-03-02 11:59
 */
@Slf4j
abstract class DslDelegate extends Script implements ParamDelegate, HttpDelegate {

}
