package example.company.jse.fiddle;

import java.io.IOException;
import java.util.Base64;

import org.junit.Test;

import example.company.tox.asn.AsnTox;
import example.company.tox.common.Tox;
import xdptdr.asn.elements.AsnElement;
import xdptdr.asn.utils.AsnUtils;
import xdptdr.common.Common;

public class Fiddle52 {

	@Test
	public void fiddle1() throws IOException {

		String s = "308204BC020100300D06092A864886F70D0101010500048204A6308204A20201000282010100ADAF60EE8A37856BEF71650FDF517A5FA9E0EBB1EF9FF0CE5F3AB3F40F66C1C460C982D6118482514863F48EF5A8C1C0CCD546C7E1871DBBAEE75A92A7CA64441C0358786869530A95CA39E01711C7BABCCC9CEA1E7AB7A7D8447615A6752DF22F357A8C661C90A6705A90F6A0546431D86E763571414F67A89BD2EADBD173B75E45BB8A7038B73A705D9DB982BC7C30C3317492A09ECD1B290276B06199C6C50CC6FEFEF91709B4E0801B9E1BA1270504CAE02C3C1188E8AED319D1CD44DE27E98D0901DAB60E0287767B7CE18918D94F5F37D96686F78FF4C14BAEB1424F67F253CC879CA46C5D5C4D3E334AA14C2CD5E2F309F1CA8251870612BD6D702A630203010001028201002B335D2BD80B394F808E36F1FCBCFA704303E3FC2D3B1E1D66C8662C458C6C93D8F3266CF28C801191ACF30E80020AB756FB91907403349D13430E5F3E06F46B908030883A88401478EF84F7BD253A05B3A23D416A7561AB08FAF6582A40293E7D099342CABF3E2777F8BEE7E9373478C579AC435CB93EF3EC975D9DF73120D3C926818A6CD7996E78134FFB3E2EB9A2C528486BA6C7BBD179F5FA8D01B960FF4C0AFA94D3B8D77B8B93AB7891DBE8C90E60AA0A7214E8CF2C51CA5944A0D5502264F722AF44BD93361E3FF42B567045D55843C4D9C99DF671A63931B4C3F339298115531F06A1A8689F74DCCF49D3DFFC09E58E17EF1E2C94C1DAD19A010E8902818100E1A958B97097DD8C5A9AF5094D3F610CE928B95F35629CC2BC99305805C88D8732FE5AC394F3281C1B14FF347049F01E5D01E5271CF124E67ABA6754565EAE114911C9815485A5571108E84FBEDE8E72F7036B55B49075A4B6381C0582D088655E90B5A9B8DDB8B13BD4FE7FBB82FAAE24655E87CE48D7280F2CFBC5BDFBA4CD02818100C50925193BC6C1BEC55F47940C4FD5271B14E0A43CBE5AC296FAA8D8287512D95B4B7F7073BD6FEBCCDBE67EC9684A782BD95E99D37CE0632D5816065077F159643CB84A929ACF48BB9D74FC4E2C888AD93A88EB9273C55E7987057AC267091FDED779FD9F872E8F560238B167B8AD06258081EE158AB675527EE7B4911E8BEF0281807618E4601836C23183F178AFA787B5C8C5F5141E6997F4264E0175B57B415C201765DA26653B64106173F9A37DE8940BAFED1FF9FA419168356E7C493CF24BF2ABE4ED2A0C0BED5DC5827DE1CE2837309F6960B0F7D06A610B240E362C21EA5FF2BF94F2FFD6B9A3B7865EB28675A3AFA34891D24CF8FDF0EF1041AB5923EB3902818029F6E6A5D72B5FBC47C1213790DFB7E01A4E7A01C0C632025BB18B206E8D70B446537012FD338D90C512D8A68C32E019740D59AE9D726D3FF23A5E867DC0EDF041FF04A92D4C3775A02FCAE8A9A5A6771C08F17C6AB6A984939184433F003EDB40D4D7B082B781DD194E63D0B4EC8A3D52E369C8D161B7B689196A33F4EC7295028180624853BF5834C9E8E7BB3CACDE05496777B1B84F1E14F478C323E6DB945FB2A38EC46CCE0D17068A38F00C7784DE4D3EEB9D8AB521038F421A998A9F3E440F7444ED5BC0E5BFD0E5FA44AB82D9D6890DF484DE278CEAB754D5B76260E19C0E2784E7ED5CC8774E6BA070CA2C616F2EC1355395F924A6B3E0E55C762AF10DC412";
		byte[] bytes = Common.bytes(s);

		AsnElement asn = AsnUtils.parse(bytes);
		Tox.print(new AsnTox().tox(asn), System.out);

		/*
		 * 
		 * File f = new File("privateKey.youpi"); FileOutputStream fos = new
		 * FileOutputStream(f);
		 * 
		 * IOUtils.copy(new ByteArrayInputStream(bytes), fos);
		 * System.out.println(f.getAbsolutePath());
		 */
		
		byte[] bytes2 = asn.asSequence().getOctetString(2).getValue();
		Tox.print(new AsnTox().tox(AsnUtils.parse(bytes2)), System.out);

		System.out.println(Base64.getEncoder().encodeToString(bytes2));
		


	}

	@Test
	public void fiddle2() {

		String s = "MIIEogIBAAKCAQEAoRwL8J/Evy5YhdEzsTQBPZGC1JeC2FsyckHxhpFam4re4iQF"
				+ "AlX82/5a3n79a1OfIcJmFn1hGoInOjfumPbmBm9KhHFbcXr6ZKfxLC3LQPg3qfMT"
				+ "dV6EsroYIwPOkdd8r2fui6+PImM85LL5sPIkke1PbPnylKKL/Wp1NsyDg1Gl6Ptp"
				+ "GKKk4kDQqfgzKo/QgL4n1OG64GpiayW48cXs8SMkxgPTi7BXWootGYF13BlicBUU"
				+ "V2rQLFfrxdcOZImqtRyYKyR18KaXbgZPhW93IJElJu3ch+m8fJG91ErR7zkEV3pM"
				+ "wz+oRuojKVkD4iEV+yz9ssKPfEOWtIzakCr/1QIDAQABAoIBACUqIR763Bn2CPml"
				+ "jrUb+AVJ4dJEuSxLrVFqINsYALzMximIgZdOWqws+q1EwhTWD2lsPLyHY7UHvSDS"
				+ "8jlbCykE1z3CIIQHCGfbzeiRD8gG+mA78IssqJPKFfNN14MRIHQeF4sljnEyT0O6"
				+ "fZrx4LnA8oeDCRB8hqqIuk1P1C6gc2DnF6955guBIlzlbf07Ys/pMhVac3ZoWIzr"
				+ "mJBeAZP9QJ46RMpIUu7dORcKgArUSdRJlAp6MVU9xLR4H6KyzmbZloZ+7798lQsn"
				+ "i9OkttdU6b7duCfCgRfoPsHsdWyT9CB3oGc6qz8R8jYhez19KR7378T+77OHzYEm"
				+ "x0yLbOkCgYEAy1a+4utIMD3c3aEDNDdwH6mujrziZUMU0R7yKHJgvcCJddTSW+eh"
				+ "xU4QbbDHUBz04/1xhzcvuJ0WCkMxdhjbmWPCgbgR27eo4R9mZY1W9/Sgd+EfY2Ia"
				+ "sBpaO5bWmkquGkPPtEWMl3KJebu5cXt1Up0a/AMEW/rTCpqyzgCbRU8CgYEAytWF"
				+ "ogGSChK1eCGmRjHJxQljFH7XmEAIvb5Ul+pFAv5ck8F+XKO7tiF4bXsFr6oBBi04"
				+ "w2njLT53UuSKxUHIiG78Uh8gLc62Cm/fOnwIMtW4GdHjn20WZDo7MGGAa6UPqSWW"
				+ "Dr1ijlhE3oe9a1YJFwzoRSIzgR7lM4XZOoeXJ5sCgYA7FXqj7x/rvJ4H3DhVHOK8"
				+ "oI5zNVlHeUV8OlS9AfcwS9jlXzC8ucKc1UEazmor0i4qbUNmuddo+mQkGUAvA6wT"
				+ "lxQ2oyi1QlmVCI21Zmtl4zQZgVWgiCofD5k2hW7YKGkMgrXAuRDQFNzzfWO2INyH"
				+ "Y9Uqcyn6HBjklOztxOlZKwKBgFwGgDi+iam0T00Px0a1tBuHZMKZwes7sjuULQE3"
				+ "stPm383RzijtyG6bxO23ER9f/FL3FXfjhMS0QKMFOcjBTeBIqoX/xTPDQ2h81UTN"
				+ "nVKjEIH2blWYwr36hXwv4Hn2S6KsgOZzGlIGzkhAQbp/MGSa4I7tM3zzrX2wzpv1"
				+ "hKjdAoGAbnj03lqjGJkwIGpik9Rq6LxP5UFGhGRvWfs23gui1lQnFVb0Sk0mS8lA"
				+ "Ho7wziTgplSvhWqI+zIxwUIoPJOoWtSDFecR/UIH4HfQWV0U5eu3sJxGe7XnICho"
				+ "f6pND7Sks2LduEVnMXzdIoH40C5F8xovQuhE3znILcgBq3jY9Lw=";

		byte[] bytes = Base64.getDecoder().decode(s);
		
		Tox.print(new AsnTox().tox(AsnUtils.parse(bytes)), System.out);
	}
}
