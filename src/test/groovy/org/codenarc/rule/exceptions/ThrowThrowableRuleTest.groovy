/*
 * Copyright 2008 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.codenarc.rule.exceptions

import org.codenarc.rule.AbstractRuleTest
import org.codenarc.rule.Rule

/**
 * Tests for ThrowThrowableRule
 *
 * @author Chris Mair
 * @version $Revision$ - $Date$
 */
class ThrowThrowableRuleTest extends AbstractRuleTest {
    void testRuleProperties() {
        assert rule.priority == 2
        assert rule.name == 'ThrowThrowable'
    }

    void testApplyTo_Violation() {
        final SOURCE = '''
            class MyClass {
                void myMethod() {
                    throw new Throwable()
                }
            }
        '''
        assertSingleViolation(SOURCE, 4, 'throw new Throwable()')
    }

    void testApplyTo_Violation_FullPackageName() {
        final SOURCE = '''
            class MyClass {
                void myMethod() {
                    if (error) {
                        throw new java.lang.Throwable('something bad')
                    }
                }
            }
        '''
        assertSingleViolation(SOURCE, 5, "throw new java.lang.Throwable('something bad')")
    }

    void testApplyTo_NoViolation() {
        final SOURCE = '''class MyClass {
                def myMethod() {
                    try {
                        throw new Exception()      // ok
                    } finally {
                        println 'ok'
                    }
                    throw new Exception()          // ok
                }
            }'''
        assertNoViolations(SOURCE)
    }

    protected Rule createRule() {
        return new ThrowThrowableRule()
    }

}