package org.orbisgis.osm

import org.junit.jupiter.api.Test
import org.orbisgis.datamanager.h2gis.H2GIS
import org.orbisgis.datamanagerapi.dataset.ITable

import static org.junit.jupiter.api.Assertions.*

/**
 * Test class for the processes in {@link Loader}
 *
 * @author Sylvain PALOMINOS (UBS LAB-STICC 2019)
 */
class LoaderTest {

    private static final def PATH = "./target/"
    private static final def DB_OPTION = ";AUTO_SERVER=TRUE"
    private static final def uuid(){ "_"+UUID.randomUUID().toString().replaceAll("-", "_")}

    protected static final def RANDOM_DS = {H2GIS.open(PATH + uuid() + DB_OPTION)}

    @Test
    void badLoadTest(){
        H2GIS ds = RANDOM_DS()
        def load = OSMTools.Loader.load()
        assertNotNull load
        def url = LoaderTest.getResource("sample.osm")
        assertNotNull url
        def osmFile = new File(url.toURI())
        assertTrue osmFile.exists()
        assertTrue osmFile.isFile()
        def prefix = uuid().toUpperCase()

        //Null dataSource
        assertFalse load([datasource: null, osmTablesPrefix: prefix, osmFilePath: osmFile.absolutePath])
        assertNotNull load.results
        assertTrue load.results.isEmpty()

        //Null prefix
        assertFalse load([datasource: ds, osmTablesPrefix: null, osmFilePath: osmFile.absolutePath])
        assertNotNull load.results
        assertTrue load.results.isEmpty()
        //Bad prefix
        assertFalse load([datasource: ds, osmTablesPrefix: "(╯°□°）╯︵ ┻━┻", osmFilePath: osmFile.absolutePath])
        assertNotNull load.results
        assertTrue load.results.isEmpty()

        //Null path
        assertFalse load([datasource: ds, osmTablesPrefix: prefix, osmFilePath: null])
        assertNotNull load.results
        assertTrue load.results.isEmpty()
        //Unexisting path
        assertFalse load([datasource: ds, osmTablesPrefix: prefix, osmFilePath: "ᕕ(ᐛ)ᕗ"])
        assertNotNull load.results
        assertTrue load.results.isEmpty()
    }
    @Test
    void loadTest(){
        H2GIS ds = RANDOM_DS()
        def load = OSMTools.Loader.load()
        assertNotNull load
        def url = LoaderTest.getResource("sample.osm")
        assertNotNull url
        def osmFile = new File(url.toURI())
        assertTrue osmFile.exists()
        assertTrue osmFile.isFile()
        def prefix = uuid().toUpperCase()

        assertTrue load([datasource: ds, osmTablesPrefix: prefix, osmFilePath: osmFile.absolutePath])

        //Test on DataSource
        def tableArray = ["${prefix}_NODE", "${prefix}_NODE_MEMBER", "${prefix}_NODE_TAG",
                          "${prefix}_WAY", "${prefix}_WAY_MEMBER","${prefix}_WAY_TAG", "${prefix}_WAY_NODE",
                          "${prefix}_RELATION", "${prefix}_RELATION_MEMBER", "${prefix}_RELATION_TAG"] as String[]
        tableArray.each { name ->
            assertNotNull ds.getTable(name), "The table named $name is not in the datasource"
        }

        //NODE
        //Test on NODE table
        def nodeTable = ds.getTable(tableArray[0])
        assertNotNull nodeTable
        assertEquals 5, nodeTable.rowCount
        def arrayNode = ["ID_NODE", "THE_GEOM", "ELE", "USER_NAME", "UID", "VISIBLE", "VERSION", "CHANGESET",
                     "LAST_UPDATE", "NAME"] as String[]
        assertArrayEquals(arrayNode, nodeTable.columnNames as String[])
        nodeTable.eachRow { row ->
            switch(row.row){
                case 1:
                    assertEquals "256001", row.ID_NODE.toString()
                    assertEquals "POINT (32.8545692 57.0465758)", row.THE_GEOM.toString()
                    assertNull row.ELE
                    assertEquals "UserTest", row.USER_NAME.toString()
                    assertEquals "5001", row.UID.toString()
                    assertEquals "true", row.VISIBLE.toString()
                    assertEquals "1", row.VERSION.toString()
                    assertEquals "6001", row.CHANGESET.toString()
                    //assertEquals "2012-01-10T23:02:55Z", row.LAST_UPDATE.toString()
                    assertEquals "2012-01-10 00:00:00.0", row.LAST_UPDATE.toString()
                    assertEquals "", row.NAME.toString()
                    break
                case 2:
                    assertEquals "256002", row.ID_NODE.toString()
                    assertEquals "POINT (32.8645692 57.0565758)", row.THE_GEOM.toString()
                    assertNull row.ELE
                    assertEquals "UserTest", row.USER_NAME.toString()
                    assertEquals "5001", row.UID.toString()
                    assertEquals "true", row.VISIBLE.toString()
                    assertEquals "1", row.VERSION.toString()
                    assertEquals "6001", row.CHANGESET.toString()
                    //assertEquals "2012-01-10T23:02:55Z", row.LAST_UPDATE
                    assertEquals "2012-01-10 00:00:00.0", row.LAST_UPDATE.toString()
                    assertEquals "", row.NAME.toString()
                    break
                case 3:
                    assertEquals "256003", row.ID_NODE.toString()
                    assertEquals "POINT (32.8745692 57.0665758)", row.THE_GEOM.toString()
                    assertNull row.ELE
                    assertEquals "UserTest", row.USER_NAME.toString()
                    assertEquals "5001", row.UID.toString()
                    assertEquals "true", row.VISIBLE.toString()
                    assertEquals "1", row.VERSION.toString()
                    assertEquals "6001", row.CHANGESET.toString()
                    //assertEquals "2012-01-10T23:02:55Z", row.LAST_UPDATE.toString()
                    assertEquals "2012-01-10 00:00:00.0", row.LAST_UPDATE.toString()
                    assertEquals "", row.NAME.toString()
                    break
                case 4:
                    assertEquals "256004", row.ID_NODE.toString()
                    assertEquals "POINT (32.8845692 57.0765758)", row.THE_GEOM.toString()
                    assertNull row.ELE
                    assertEquals "UserTest", row.USER_NAME.toString()
                    assertEquals "5001", row.UID.toString()
                    assertEquals "true", row.VISIBLE.toString()
                    assertEquals "1", row.VERSION.toString()
                    assertEquals "6001", row.CHANGESET.toString()
                    //assertEquals "2012-01-10T23:02:55Z", row.LAST_UPDATE.toString()
                    assertEquals "2012-01-10 00:00:00.0", row.LAST_UPDATE.toString()
                    assertEquals "Just a house node", row.NAME.toString()
                    break
                case 5:
                    assertEquals "256005", row.ID_NODE.toString()
                    assertEquals "POINT (32.8945692 57.0865758)", row.THE_GEOM.toString()
                    assertNull row.ELE
                    assertEquals "UserTest", row.USER_NAME.toString()
                    assertEquals "5001", row.UID.toString()
                    assertEquals "true", row.VISIBLE.toString()
                    assertEquals "1", row.VERSION.toString()
                    assertEquals "6001", row.CHANGESET.toString()
                    //assertEquals "2012-01-10T23:02:55Z", row.LAST_UPDATE.toString()
                    assertEquals "2012-01-10 00:00:00.0", row.LAST_UPDATE.toString()
                    assertEquals "Just a tree", row.NAME.toString()
                    break
                default:
                    fail()
            }
        }

        //Test on NODE_MEMBER table
        def nodeMemberTable = ds.getTable(tableArray[1])
        assertNotNull nodeMemberTable
        assertEquals 2, nodeMemberTable.rowCount
        def arrayNodeMember = ["ID_RELATION", "ID_NODE", "ROLE", "NODE_ORDER"] as String[]
        assertArrayEquals(arrayNodeMember, nodeMemberTable.columnNames as String[])
        nodeMemberTable.eachRow { row ->
            switch(row.row){
                case 1:
                    assertEquals "259001", row.ID_RELATION.toString()
                    assertEquals "256004", row.ID_NODE.toString()
                    assertEquals "center", row.ROLE.toString()
                    assertEquals "2", row.NODE_ORDER.toString()
                    break
                case 2:
                    assertEquals "259001", row.ID_RELATION.toString()
                    assertEquals "256005", row.ID_NODE.toString()
                    assertEquals "barycenter", row.ROLE.toString()
                    assertEquals "3", row.NODE_ORDER.toString()
                    break
                default:
                    fail()
            }
        }

        //Test on NODE_TAG table
        def nodeTagTable = ds.getTable(tableArray[2])
        assertNotNull nodeTagTable
        assertEquals 2, nodeTagTable.rowCount
        def arrayNodeTag = ["ID_NODE", "TAG_KEY", "TAG_VALUE"] as String[]
        assertArrayEquals(arrayNodeTag, nodeTagTable.columnNames as String[])
        nodeTagTable.eachRow { row ->
            switch(row.row){
                case 1:
                    assertEquals "256004", row.ID_NODE.toString()
                    assertEquals "building", row.TAG_KEY.toString()
                    assertEquals "house", row.TAG_VALUE.toString()
                    break
                case 2:
                    assertEquals "256005", row.ID_NODE.toString()
                    assertEquals "natural", row.TAG_KEY.toString()
                    assertEquals "tree", row.TAG_VALUE.toString()
                    break
                default:
                    fail()
            }
        }

        //WAY
        //Test on WAY table
        def wayTable = ds.getTable(tableArray[3])
        assertNotNull wayTable
        assertEquals 1, wayTable.rowCount
        def arrayWay = ["ID_WAY", "USER_NAME", "UID", "VISIBLE", "VERSION", "CHANGESET",
                         "LAST_UPDATE", "NAME"] as String[]
        assertArrayEquals(arrayWay, wayTable.columnNames as String[])
        wayTable.eachRow { row ->
            switch(row.row){
                case 1:
                    assertEquals "258001", row.ID_WAY.toString()
                    assertEquals "UserTest", row.USER_NAME.toString()
                    assertEquals "5001", row.UID.toString()
                    assertEquals "true", row.VISIBLE.toString()
                    assertEquals "1", row.VERSION.toString()
                    assertEquals "6001", row.CHANGESET.toString()
                    assertEquals "2012-01-10 23:02:55.0", row.LAST_UPDATE.toString()
                    assertEquals "", row.NAME.toString()
                    break
                default:
                    fail()
            }
        }

        //Test on WAY_MEMBER table
        def wayMemberTable = ds.getTable(tableArray[4])
        assertNotNull wayMemberTable
        assertEquals 1, wayMemberTable.rowCount
        def arrayWayMember = ["ID_RELATION", "ID_WAY", "ROLE", "WAY_ORDER"] as String[]
        assertArrayEquals(arrayWayMember, wayMemberTable.columnNames as String[])
        wayMemberTable.eachRow { row ->
            switch(row.row){
                case 1:
                    assertEquals "259001", row.ID_RELATION.toString()
                    assertEquals "258001", row.ID_WAY.toString()
                    assertEquals "outer", row.ROLE.toString()
                    assertEquals "1", row.WAY_ORDER.toString()
                    break
                default:
                    fail()
            }
        }

        //Test on WAY_TAG table
        def wayTagTable = ds.getTable(tableArray[5])
        assertNotNull wayTagTable
        assertEquals 1, wayTagTable.rowCount
        def arrayWayTag = ["ID_WAY", "TAG_KEY", "TAG_VALUE"] as String[]
        assertArrayEquals(arrayWayTag, wayTagTable.columnNames as String[])
        wayTagTable.eachRow { row ->
            switch(row.row){
                case 1:
                    assertEquals "258001", row.ID_WAY.toString()
                    assertEquals "highway", row.TAG_KEY.toString()
                    assertEquals "primary", row.TAG_VALUE.toString()
                    break
                default:
                    fail()
            }
        }

        //Test on WAY_NODE table
        def wayNodeTable = ds.getTable(tableArray[6])
        assertNotNull wayNodeTable
        assertEquals 3, wayNodeTable.rowCount
        def arrayWayNode = ["ID_WAY", "ID_NODE", "NODE_ORDER"] as String[]
        assertArrayEquals(arrayWayNode, wayNodeTable.columnNames as String[])
        wayNodeTable.eachRow { row ->
            switch(row.row){
                case 1:
                    assertEquals "258001", row.ID_WAY.toString()
                    assertEquals "256001", row.ID_NODE.toString()
                    assertEquals "1", row.NODE_ORDER.toString()
                    break
                case 2:
                    assertEquals "258001", row.ID_WAY.toString()
                    assertEquals "256002", row.ID_NODE.toString()
                    assertEquals "2", row.NODE_ORDER.toString()
                    break
                case 3:
                    assertEquals "258001", row.ID_WAY.toString()
                    assertEquals "256003", row.ID_NODE.toString()
                    assertEquals "3", row.NODE_ORDER.toString()
                    break
                default:
                    fail()
            }
        }

        //RELATION
        //Test on RELATION table
        def relationTable = ds.getTable(tableArray[7])
        assertNotNull relationTable
        assertEquals 1, relationTable.rowCount
        def arrayRelation = ["ID_RELATION", "USER_NAME", "UID", "VISIBLE", "VERSION", "CHANGESET",
                        "LAST_UPDATE"] as String[]
        assertArrayEquals(arrayRelation, relationTable.columnNames as String[])
        relationTable.eachRow { row ->
            switch(row.row){
                case 1:
                    assertEquals "259001", row.ID_RELATION.toString()
                    assertEquals "UserTest", row.USER_NAME.toString()
                    assertEquals "5001", row.UID.toString()
                    assertEquals "true", row.VISIBLE.toString()
                    assertEquals "1", row.VERSION.toString()
                    assertEquals "6001", row.CHANGESET.toString()
                    assertEquals "2012-01-10 23:02:55.0", row.LAST_UPDATE.toString()
                    break
                default:
                    fail()
            }
        }

        //Test on RELATION_MEMBER table
        def relationMemberTable = ds.getTable(tableArray[8])
        assertNotNull relationMemberTable
        assertEquals 0, relationMemberTable.rowCount
        def arrayRelationMember = ["ID_RELATION", "ID_SUB_RELATION", "ROLE", "RELATION_ORDER"] as String[]
        assertArrayEquals(arrayRelationMember, relationMemberTable.columnNames as String[])

        //Test on RELATION_TAG table
        def relationTagTable = ds.getTable(tableArray[9])
        assertNotNull relationTagTable
        assertEquals 2, relationTagTable.rowCount
        def arrayRelationTag = ["ID_RELATION", "TAG_KEY", "TAG_VALUE"] as String[]
        assertArrayEquals(arrayRelationTag, relationTagTable.columnNames as String[])
        relationTagTable.eachRow { row ->
            switch(row.row){
                case 1:
                    assertEquals "259001", row.ID_RELATION.toString()
                    assertEquals "ref", row.TAG_KEY.toString()
                    assertEquals "123456", row.TAG_VALUE.toString()
                    break
                case 2:
                    assertEquals "259001", row.ID_RELATION.toString()
                    assertEquals "route", row.TAG_KEY.toString()
                    assertEquals "bus", row.TAG_VALUE.toString()
                    break
                default:
                    fail()
            }
        }
    }

}
