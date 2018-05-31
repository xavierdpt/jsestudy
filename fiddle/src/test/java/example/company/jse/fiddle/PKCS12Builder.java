package example.company.jse.fiddle;

import example.company.asn.AsnEncoding;
import example.company.asn.elements.Asn;
import example.company.asn.elements.AsnElement;
import example.company.asn.elements.AsnOctetString;
import example.company.asn.elements.AsnSequence;
import example.company.asn.utils.OIDS;
import example.company.tox.common.Common;

public class PKCS12Builder {

	public static byte[] pbeSha3DESCBCs = Common.bytes("8FD2439C4F78EB6612D94F642C7B75FBBF5FF371");
	public static byte[] something3s = Common.bytes("1B9DCA53442A0222F5DA254DBBC62C7C6FD2E60E");

	public static byte[] something2 = Common.bytes(
			"00BB2D09B15E7D130B72DC4D3D1487BFE681CCE75D7506AB3190BC894F86DB5401D6B227558D657AE8F36B716795EAEF68C52109235F81B8256852B832A16655624A99FCBCBA3005621926B4EB4D1307E0213FBDEDA475FE684B4D2A7B7769A20DE07FA0444C44A8B6FD15930472B1B75A213710172FE687935D50EAE52A8E43FC829B170BD33CAB76335B8C5FA33C4BCAAD015B9A1F8D5AD07A4C3918E9B25B1A96D872641BFA97EFFF75B316B70A538B381F6A9F95DD7B945478A9B04D3B9471631B7F70FB50925F7681770B18AD7E96F4428E1A5841CAC5FAD75CA782D9D500CDE0F61C153739FCBDC101DC2E1974FA16B86F7255BCE6D56DB3C181CD6EB949879F9F04D3591E1F0AC1754693251567D91EE5D43F0CD17B6E017A82DDCD180138D11E879CD9D763A6DD88CD07A3C1551CF01C94202E7B3FF270EAEE2D204A798F766E2BA894C93F1467B868D9BE86490A60C06F5215FF31A8CC6B93C9AC722F2650FFA583BC7D905CB40C4E33AFB0A30CEE7C66E4B802062AFDA9C521E9404F0BFA44D925B853A626D418670278DA26279EE8B745E462794F38D54A6550D07D87EA36FD3632D412753548D83ED475222F42B1676137E382FC651299E584D5A47BCBB9FD63E71CD2D6591C0BD9FDE644A022561BCA0BEE624B7D39B235B33DB9B300062E9A76FEDCB0C535B40C32B24E5AF72858BC4E0D4E41554A385BEDEDE83D241188BEFD0B0D60A8CC121A30D6955DFAD85A503CD3E5930579634BC46FF8D0C20AF27DC8E794CAC2AAB1BA59F5055C755C10121C4BDC0021FE3F074D40A38FC0518A1E6F3C438F848BA24FD27BC64B8DEAF495D4FED02C2DEE083CB12B5123D3859D1D4AA7929683F1994DD7DA3DDC6D14F2FC82BC9A812D0E102EEA20582B7A174169E52C01C57FE17147206B27C9D4FE87D2FF58B2A28209DAE01A2A4877FB0C8948078B6CB48B3BC4B2A67D474EAA0E448A143E6162F9278DA8B40B8EF6E7282D0C2F3D4F9398C5448A4BFD8BCC46D76C94EA49023DB68DC3AE8A324B156095E9A2AA387AE07A0EA759632C62281B311F67C62476319524FFE80E1D8921C3C88018D8D6DD2E337113A74922CFD23E50C209646724CE96F7A06C590C61CDE99998F551158FC030C0949BEF05D490CC721E73D76D2888E2DE757524F62AA04A172F1178D8E74EC1CA1B56E8F76E1EB039B4B73F770E047E9F202FD3C88F00D14B1B73A4AA4FB5E3B5E5AFD1E16537FDBB420609B471B00BDBE4A56E2AF374E5715A29D3B91C552BCB59B5B522C22E0AA818B42A90FEB6D08FFC78A00A1BE41A1291B8BF88C53E1EA3C57C50764D7631BA1D152A3A1E4D52409F55A5B7DAC7C0BD60AD2537DCCA009827E092A81EE3CDF62FB544A7BF160A2A3D1B5E1AC8C9879AEF4D5717ABA4938B10EAA27D0DF468287B180426508F3F22A7096A1AAAF6290FEED681BBF2C367D4EB0B54A882DE4BA4FC029AA30FEA4B33E40F0DC1CC530AC2571990680AEB143F2E0ED8C04ED4D901F4D557E3697014D1A2B27032F8ED6D5A7CD0D7FD8AB648FFB0CC832D563C60DA6DEED545108E8013B0AD22BA7861944AD4220DD2BF814214D82BB9BC363CFC23273BFD886A1807B53B71F276B26DD1B44B573CE185B2F1F84B713015CF56A62133F8F6BEE63B69B6EF399C1DFF2719C4CCD7425AF55F2999E715A1F5C56A524824123460D5B00557F1F526C6E277D8A21E1E84D7");
	public static byte[] encryptedCertificate = Common.bytes(
			"213B10AF5C876EA8D6B8DFF745253BCF6FCD8FFD89FBF200874633CF068E516D53E8F7FAA0CE51B02690F173FE219C5D69B84AA0AE978DF28C654D7FBA86FFC687B922DC8F29621EAB0E81CCF43B45EF0CB4E22CB4AA7878615B64EE4FF116F42789C7D67AE07C16DC5D84BB07239AF2384B7881D1E1295936DEA5FDA4FB133861639804796BA272625EF04BDA29DCED87DFE653708906AF0A80F87B6941268D84AFBD0F78BD14BCFFBA263A80D5F93EC4D9455F7548987425CD165F996B1AD17B5AD5ED460621921640ACF9E93935A05DBA7C79364071B666C415144843FDEDF2DFAC3580456EC22F418C27764847AE31B73CA1AFFC98E43A2136CA06EC945C6CA50C14C0222EA8BD00BBCBB932BF37F6B09DA758354296199D4DE0D98B33CC3C4687F4A01158860841704A229465460FD1703C280AD7E7F7C14FA7256E4088C83A510D15D4F5EE0D910E3C5FDA2AC057BFC8DECA2030717D51958E48A09C37F50DF96E70D1A44F79264A40D0A9EB08B02675156BBF8291545F2B65ECC60C3A2416E0BC3B2E9C5823B25F285B3EF8A6B67DA7751CCA1C904533987ABD7F0DCC2B0A3A0AEBA6C07E58ABAE7DE4C6985D714F4C5FFAD1E49C620FDC2DC9BA1AD459FC681241118728C8A3BB3303F9771128BC041BA15F44BA353796BBE10DEAA74BDB7C54FAE871ECD48B4FCEEF08B736F51F8D7AF6B3FC682728FD3C8BF5DCDFBBCE36111DF11032768E4F754E16B326BD9C75BC136994AC2FB0231218538476A2D8B7EE7AD9629025E22D6864936A0195E947F7E22F77E91AF18BBBC6C71F43C6C696F44621A1F76974D89A2BDA4FF00B778F232EFFEBE7B6A8A089F8280B96B4DE784FD2CE1E9E38EF972ED1E5D581D570DDA8697F8E8B8E364C809E94332D29E4915A4E4D168978B7B6C56F237437344BFD89C607DB9F2BFA81B338E98C8345A80E0D0BBDBD0BBECE1A7437A1F02FD2C0EC483CDF26D69CE38FF9BD5E3983CD0E0E3121FAD4C309F7355492F5E093CD99F89CF1229F0508429A0C4371044083ACEBDE68FDCAD4EB3D69333F09794C3AC86E67D35CA2490606AF3EDA4FD35D40F0E28662E20F387B33C3677E88ECAE4622F2B7D0628E1B59AB7BE9443F21B53153EB83D379244AA41F37AF26E384AC7AC27DAA44BFC0DE45B05AB5673AFD6011672C5F80E7F75EE67C1F35B43DE130873D3A3B50EBF3A50DF72A67BBBB4F99C803A856AE09C967B0364E7513C81B4F5DF6EBB13B62FC65DA63B9130CF9E462610BDA4CA98C2F1E660643AA5B175517F6A71B1CD10A1B9E2DBEC2520F8C636B31DEB9E700EBB08B8CC237840BFDB95A857C23E76D088EAE1D9DB3BADAEDDCB59809307E8EDF3FD27AB77F5B8A8C558760A2D84E6363E023E6C4E06E108D61C1DAB2BA9D14198702CA645EE442EA4633CB55F8CC0C7D057F150A90DFDDE1CB05FCAE8B5F584829D0F212A956D53EE3690BFD210869F86A12FE76CE587970A48351EDDAA1B8D3E5545EB3B470C73F7295");

	public static byte[] localKeyId = Common.bytes("54696D652031353237323430363739303233");
	public static byte[] friendlyName = Common.bytes("0063006C00690065006E007400630061");

	public static byte[] macHash = Common.bytes("B72974C662F5B884D336678666D7D9F7D5877464");
	public static byte[] macSalt = Common.bytes("6CB867698D2BF58AC0ECA315C192DEC3C76AF213");
	public static int macIterationCount = 100000;
	private static int pbeIterationCount = 50000;

	public byte[] encode() {
		return getSampleAsn().encode();
	}

	public static AsnElement getSampleMac() {
		return Asn.seq(

				Asn.seq(

						Asn.seq(Asn.oid(OIDS.HASH_ALGORITHM_IDENTIFIER), Asn.n()),

						Asn.os(macHash)

				),

				Asn.os(macSalt),

				Asn.integer(macIterationCount)

		);
	}

	public static AsnElement getSampleContent() {

		return Asn.seq(

				Asn.oid(OIDS.DATA),

				Asn.cs(0, getSampleDataContent())

		);
	}

	public static AsnOctetString getSampleDataContent() {

		return Asn.os(Asn.seq(

				Asn.seq(Asn.oid(OIDS.DATA), Asn.cs(0, Asn.os(getSampleSubDataContent()))),

				Asn.seq(Asn.oid(OIDS.ENCRYPTED_DATA), Asn.cs(0, getSampleEncryptedDataContent()))

		));
	}

	private static AsnSequence getSampleSubDataContent() {
		return Asn.seq(

				Asn.seq(

						Asn.oid(OIDS.PKCS_8_SHROUDED_KEY_BAG),

						Asn.cs(0, getSampleShroudedContentAsn()),

						Asn.set(getSampleFriendlyNameAsn(), getSampleLocalKeyIdAsn())

				));
	}

	private static AsnElement getSampleLocalKeyIdAsn() {
		return Asn.seq(Asn.oid(OIDS.LOCAL_KEY_ID), Asn.set(Asn.os(localKeyId)));
	}

	private static AsnElement getSampleFriendlyNameAsn() {
		return Asn.seq(Asn.oid(OIDS.FRIENDLY_NAME), Asn.set(Asn.bmpstring(friendlyName)));
	}

	private static AsnSequence getSampleShroudedContentAsn() {

		return Asn.seq(

				Asn.seq(

						Asn.oid(OIDS.PBE_WITH_SHA_AND_3_KEY_TRIPLE_DES_CBC),
						Asn.seq(Asn.os(pbeSha3DESCBCs), Asn.integer(50000))

				),

				Asn.os(something2)

		);
	}

	public static AsnSequence getSamplePBSParameters() {

		return Asn.seq(Asn.os(something3s), Asn.integer(pbeIterationCount));
	}

	public static AsnSequence getSampleEncryptedDataContent() {

		return Asn.seq(

				Asn.integer(0),

				Asn.seq(

						Asn.oid(OIDS.DATA),

						Asn.seq(

								Asn.oid(OIDS.PBE_WITH_SHA_AND_40_BIT_RC2_CBC),

								getSamplePBSParameters()

						),

						Asn.cs(0, encryptedCertificate, AsnEncoding.PRIMITIVE)

				)

		);
	}

	public static AsnElement getSampleAsn() {

		return Asn.seq(

				Asn.integer(3),

				getSampleContent(),

				getSampleMac()

		);
	}
}