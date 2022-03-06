package com.example.languagecommunity.data.repository.implementations

import org.junit.runner.RunWith
import org.junit.runners.Suite

/**
 * Created by Imran Chowdhury on 3/6/2022.
 */


@RunWith(Suite::class)
@Suite.SuiteClasses(
    ICommunityPersonRepositoryTest::class,
    IPreferenceRepositoryTest::class
)
class RepositoryTestSuite