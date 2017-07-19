package com.wzj.learn.autotest.testcase;

import com.wzj.learn.autotest.utils.HttpUtil;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangzhenjiang on 6/10/17.
 */
public class TestLogin {

    @Test
    public void testLoginJD() throws IOException {

        String url = "https://passport.jd.com/uc/loginService?uuid=ff75c8a6-9692-421a-8e84-738fded1a622&ReturnUrl=https%3A%2F%2Fwww.jd.com%2F&r=0.26373833994048623&version=2015";

        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "text/plain, */*; q=0.01");
//        headers.put("Origin","https://passport.jd.com");
        headers.put("Connection", "keep-alive");
//        headers.put("Content-Length","1665");
//        headers.put("X-Requested-With","XMLHttpRequest");
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");

        Map<String, String> params = new HashMap<>();
        params.put("uuid", "ff75c8a6-9692-421a-8e84-738fded1a622");
        params.put("eid", "WTDOFRR7ZGT5OSUSQY33K674BAHUCHGRF2DW62KQ6ZYT54V7FIRFFHR2HSR5UPJXHY7QYDF2POLL6CFBCJYA5QHZIA");
        params.put("fp", "3ec217138fb4b378774f1d9db38e899e");
        params.put("_t", "_nteHNir");
        params.put("loginType", "f");
        params.put("loginname", "18682095432");
        params.put("nloginpwd", "IzTIKZ04OyQW9y/J+9C5YZGK1mQEb6S+lxpQoBvVmsWx82KBpJtd2s7DWmp1SrfreVQ7PQEUZKHWrWQ+lh5BrLBs3eiQEzdB+zj6fwQKTiZ0C8JIIhzGsWjVR8uNtOXqc8ddrpGJrB9bnjuwgjPV+ZQvHSAUX0waL2Mk1JxnBdk=");
        params.put("pubKey", "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDC7kw8r6tq43pwApYvkJ5laljaN9BZb21TAIfT");
        params.put("sa_token", "B68C442BE645754F33277E701208059080DD726A94A73F76DEC3053A838549C06EB7D3797CE1C5BBE7C2B2EF9CA7D467C820D6D2743D5D610B7AEC435D4160C8D69FD80591E9CF58440F6D8C144D610994FFCFF0A42DBD7FAEDE5D7FECED53B6A762C3D9E3C78BD6B494F0FBE0AFE52C4F8DB2BC43BC9D3304EEFEB1BF355FC2AB4C0EAA98940CBFA40A312A0D28910D53C8B002D379997E8E82676E2B3FCC9EB2471EBC715B38E4433F782BAC66F9467FA3EF88925B9E7028A17DE806DD14E38D5A865C8C5FA7F1E05BEAF332A03F1E7828230F939A481ED6709646D4EDF6213B3897F5FE3FFA00C297E63D05FA54AB497788A754CB07E32046103680D42B41CDFEF14019E1636DF60C3EED17E88CE58AA1ADAFEFB6840441ED82BF646085615F730553BAC29472D132599CBBB31712913446E7BB40F38373AFEE1F2D6A1FFD8F2D68982F0C35227A16F6D164C61B9A98F15DFA68D62B4B7B78C06DEF32FD01098ED23C282D12049369300EB380017F6A0B47D781E5B6BE87421E506BBDE14D918292D714DB50B12F6FD5D6647F146BB6D06909EF7AF2FE7D72C067F139DDEA5B1EBC28ACB341BCC90F99D49B112C3A3D7FF28205539A5B5FF2C280D878B7419D78FDF69DB1DCF9DFAC7CDED8422D3A4F1DEC0E74C7AB7AB91ABB16536736AA");
        params.put("seqSid", "3259819117999454001");

        CookieStore cookieStore = new BasicCookieStore();
        String response = HttpUtil.doPost(url, params, headers, cookieStore);
        System.out.println(response);
        Reporter.log(response);
    }
}
